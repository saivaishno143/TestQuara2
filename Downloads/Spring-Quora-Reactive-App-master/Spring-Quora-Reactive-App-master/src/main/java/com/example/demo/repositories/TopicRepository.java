package com.example.demo.repositories;

import com.example.demo.models.Topic;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TopicRepository  extends ReactiveMongoRepository<Topic, String> {
}
