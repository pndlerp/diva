package com.bebrample.diva.common.security;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service

public class CookieService {

    public ResponseCookie createCookie(String token){
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(24))
                .build();
    }

    public ResponseCookie deleteCookie() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(0))
                .build();
    }
}
