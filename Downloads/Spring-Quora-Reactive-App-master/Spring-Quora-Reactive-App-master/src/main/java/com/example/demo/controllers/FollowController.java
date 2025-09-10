package com.example.demo.controllers;

import com.example.demo.dto.FollowDTO;
import com.example.demo.services.IFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final IFollowService followService;

    @PostMapping("/follow")
    public Mono<Void> follow(@RequestBody FollowDTO followDTO) {
        return followService.follow(followDTO);
    }

    @PostMapping("/unfollow")
    public Mono<Void> unfollow(@RequestBody FollowDTO followDTO) {
        return followService.unFollow(followDTO);
    }
}