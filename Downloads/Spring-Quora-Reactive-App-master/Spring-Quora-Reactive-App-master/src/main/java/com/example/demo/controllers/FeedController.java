package com.example.demo.controllers;

import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.services.IFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final IFeedService feedService;

    @GetMapping("/{userId}")
    public Flux<QuestionResponseDTO> getFeed(@PathVariable String userId) {
        return feedService.getFeed(userId);
    }
}
