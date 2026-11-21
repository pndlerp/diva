package com.bebrample.diva.user;

import lombok.Builder;

@Builder
public record UserResponse(String email, String nickname, String username) {
}
