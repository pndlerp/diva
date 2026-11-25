package com.bebrample.diva.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    private final Long expirationDate = 86400000L;

    public String generateToken(CustomUserDetails user){
        String authority = user.getAuthorities().iterator().next().getAuthority();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", authority)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationDate))
                .signWith(secretKey)
                .compact();
    }

    public Claims verifyToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
