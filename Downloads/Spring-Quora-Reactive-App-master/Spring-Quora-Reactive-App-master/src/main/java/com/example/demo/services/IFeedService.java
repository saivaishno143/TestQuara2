package com.example.demo.services;

import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFeedService {
    Mono<Void> fanOutQuestion(Question question);
    Flux<QuestionResponseDTO> getFeed(String userId);
}
