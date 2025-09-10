package com.example.demo.services;

import com.example.demo.dto.FollowDTO;
import reactor.core.publisher.Mono;

public interface IFollowService {
    Mono<Void> follow(FollowDTO followDTO);
    Mono<Void> unFollow(FollowDTO followDTO);
}
