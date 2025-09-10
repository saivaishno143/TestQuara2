package com.example.demo.services;

import com.example.demo.dto.TopicDTO;
import com.example.demo.models.Topic;
import com.example.demo.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {
    private final TopicRepository topicRepository;


    @Override
    public Mono<TopicDTO> createTopic(TopicDTO topicDTO) {
        Topic topic=Topic.builder()
                .name(topicDTO.getName())
                .description(topicDTO.getDescription())
                .build();
        return topicRepository.save(topic)
                .map(savedTopic->TopicDTO.builder()
                        .id(savedTopic.getId())
                        .name(savedTopic.getName())
                        .description(savedTopic.getDescription())
                        .build());
    }
}
