package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.events.QuestionCreatedEvent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.adapter.QuestionAdapter;
import com.example.demo.dto.QuestionRequestDTO;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.events.ViewCountEvent;
import com.example.demo.models.Question;
import com.example.demo.models.QuestionElasticDocument;
import com.example.demo.producers.KafkaEventProducer;
import com.example.demo.repositories.QuestionDocumentRepository;
import com.example.demo.repositories.QuestionRepository;
import com.example.demo.utils.CursorUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final KafkaEventProducer kafkaEventProducer;
    private final IQuestionIndexService questionIndexService;
    private final QuestionDocumentRepository questionDocumentRepository;
    private final NotificationService notificationService;
    private final IFeedService feedService;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {

        Question question = Question.builder()
                .title(questionRequestDTO.getTitle())
                .content(questionRequestDTO.getContent())
                .userId(questionRequestDTO.getUserId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return questionRepository.save(question) // 1. Save question to MongoDB
                .flatMap(savedQuestion ->
                        questionIndexService.createQuestionIndex(savedQuestion) // 2. Index in Elasticsearch
                                .thenReturn(savedQuestion)
                )
                .flatMap(savedQuestion ->
                        feedService.fanOutQuestion(savedQuestion) // 3. Fan-out to followers' feeds
                                .thenReturn(savedQuestion)
                )
                .map(savedQuestion -> {
                    // 4. Send notification and create response DTO
                    QuestionResponseDTO responseDTO = QuestionAdapter.toQuestionResponseDTO(savedQuestion);
                    QuestionCreatedEvent event = new QuestionCreatedEvent(responseDTO.getId(), responseDTO.getTitle(), responseDTO.getCreatedAt());
                    notificationService.sendNotification(event);
                    return responseDTO;
                })
                .doOnSuccess(response -> System.out.println("Question created successfully: " + response))
                .doOnError(error -> System.out.println("Error creating question: " + error));
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int page) {
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset, page))
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error searching questions: " + error))
                .doOnComplete(() -> System.out.println("Questions searched successfully"));
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);

        if(!CursorUtils.isValidCursor(cursor)) {
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(size)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully"));
        } else {
            LocalDateTime cursorTimeStamp = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorTimeStamp, pageable)
                    .map(QuestionAdapter::toQuestionResponseDTO)
                    .doOnError(error -> System.out.println("Error fetching questions: " + error))
                    .doOnComplete(() -> System.out.println("Questions fetched successfully"));
        }

    }

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String id) {
        return questionRepository.findById(id)
                .map(QuestionAdapter::toQuestionResponseDTO)
                .doOnError(error -> System.out.println("Error fetching question: " + error))
                .doOnSuccess(response -> {
                    System.out.println("Question fetched successfully: " + response);
                    // Trigger an event to increment the view count asynchronously
                    ViewCountEvent viewCountEvent = new ViewCountEvent(id, "question", LocalDateTime.now());
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                });
    }

    public Flux<QuestionElasticDocument> searchQuestionsByElasticsearch(String query) {
        return questionDocumentRepository.findByTitleContainingOrContentContaining(query, query);
    }
}

