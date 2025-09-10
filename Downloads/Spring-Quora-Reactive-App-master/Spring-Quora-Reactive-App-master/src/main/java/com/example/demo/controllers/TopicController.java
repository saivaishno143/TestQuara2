package com.example.demo.controllers;

import com.example.demo.dto.TopicDTO;
import com.example.demo.services.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;

    @PostMapping
    public Mono<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.createTopic(topicDTO);
    }
}