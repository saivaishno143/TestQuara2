package com.example.demo.services;

import com.example.demo.dto.TopicDTO;
import reactor.core.publisher.Mono;

public interface ITopicService {
    Mono<TopicDTO> createTopic(TopicDTO topicDTO);
}
