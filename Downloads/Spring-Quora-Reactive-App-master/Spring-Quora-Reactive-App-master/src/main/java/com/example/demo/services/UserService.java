package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;


    @Override
    public Mono<UserDto> createUser(UserDto userDTO) {
        User user=User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
        return userRepository.save(user)
                .map(SavedUser->
                        UserDto.builder().
                        id(SavedUser.getId())
                                .name(SavedUser.getName())
                                .email(SavedUser.getEmail())
                                .build());
    }
}
