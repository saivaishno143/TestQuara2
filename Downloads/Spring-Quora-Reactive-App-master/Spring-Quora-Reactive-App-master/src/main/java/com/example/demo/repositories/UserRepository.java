package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository  extends ReactiveMongoRepository<User, String> {
}
