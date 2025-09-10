package com.example.demo.services;

import com.example.demo.dto.FollowDTO;
import com.example.demo.models.Follow;
import com.example.demo.repositories.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService{
    private final FollowRepository followRepository;


    @Override
    public Mono<Void> follow(FollowDTO followDTO) {
        Follow follow=Follow.builder()
                .followedId(followDTO.getFollowedId())
                .followerId(followDTO.getFollowerId())
                .followType(followDTO.getFollowType())
                .build();
        return followRepository.save(follow).then();
    }

    @Override
    public Mono<Void> unFollow(FollowDTO followDTO) {
        return followRepository.deleteByFollowerIdAndFollowedIdAndFollowType(
                followDTO.getFollowerId(),
                followDTO.getFollowedId(),
                followDTO.getFollowType()
        );
    }
}
