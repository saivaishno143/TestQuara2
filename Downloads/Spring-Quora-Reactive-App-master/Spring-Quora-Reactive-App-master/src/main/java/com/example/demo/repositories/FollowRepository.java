package com.example.demo.repositories;

import com.example.demo.models.Follow;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FollowRepository extends ReactiveMongoRepository<Follow, String> {
    Mono<Void> deleteByFollowerIdAndFollowedIdAndFollowType(String followerId, String followedId, String followType);
}