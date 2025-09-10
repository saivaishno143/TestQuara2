package com.example.demo.services;

import com.example.demo.dto.UserDto;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserDto> createUser(UserDto userDTO);
}