package com.bebrample.diva.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Not found user"));
        return UserResponse.builder()
                .email(user.getMail())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }
}
