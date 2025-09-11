package com.example.demo.services;

import com.example.demo.adapter.QuestionAdapter;
import com.example.demo.dto.QuestionResponseDTO;
import com.example.demo.models.Question;
import com.example.demo.repositories.FollowRepository;
import com.example.demo.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService implements IFeedService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final FollowRepository followRepository;
    private final QuestionRepository questionRepository;
    private static final String FEED_KEY_PREFIX = "feed:";
    private static final int FEED_SIZE = 1000;

    @Override
    public Mono<Void> fanOutQuestion(Question question) {
        // Find all users who follow the author of the question
        return followRepository.findByFollowedIdAndFollowType(question.getUserId(), "USER")
                .flatMap(follower -> {
                    String feedKey = FEED_KEY_PREFIX + follower.getFollowerId();
                    // Push the new question ID to the follower's feed list in Redis
                    return reactiveRedisTemplate.opsForList().leftPush(feedKey, question.getId())
                            // Trim the feed to the latest 1000 entries
                            .then(reactiveRedisTemplate.opsForList().trim(feedKey, 0, FEED_SIZE - 1));
                }).then();
    }

    @Override
    public Flux<QuestionResponseDTO> getFeed(String userId) {
        String feedKey = FEED_KEY_PREFIX + userId;

        // Try to get the feed from Redis cache first
        return reactiveRedisTemplate.opsForList().range(feedKey, 0, -1)
                .flatMap(questionId -> questionRepository.findById(questionId))
                .map(QuestionAdapter::toQuestionResponseDTO)
                // If the cache is empty, generate the feed from the database
                .switchIfEmpty(generateAndCacheFeed(userId));
    }

    private Flux<QuestionResponseDTO> generateAndCacheFeed(String userId) {
        // Find all users followed by the current user
        return followRepository.findByFollowerId(userId)
                .map(follow -> follow.getFollowedId())
                .collectList()
                .flatMapMany(followedIds -> {
                    // If the user isn't following anyone, return an empty feed
                    if (followedIds.isEmpty()) {
                        return Flux.empty();
                    }
                    // Fetch the latest questions from the users they follow
                    return questionRepository.findByUserIdInOrderByCreatedAtDesc(followedIds);
                })
                .flatMap(question -> {
                    String feedKey = FEED_KEY_PREFIX + userId;
                    // Cache the generated feed in Redis for future requests
                    return reactiveRedisTemplate.opsForList().leftPush(feedKey, question.getId())
                            .thenReturn(question);
                })
                .map(QuestionAdapter::toQuestionResponseDTO);
    }
}

