package com.bebrample.diva.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("access_token")){
                        token = cookie.getValue();
                    }
                }
                if(token != null){
                    Claims claims = jwtService.verifyToken(token);

                    String username = claims.getSubject();

                    String role = claims.get("role", String.class);
                    Long userId = claims.get("userId", Long.class);
                    List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(role);

                    CustomUserDetails customUserDetails = CustomUserDetails.builder()
                            .id(userId)
                            .username(username)
                            .authorities(authorityList)
                            .build();
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                customUserDetails,
                                null,
                                customUserDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        filterChain.doFilter(request, response);
    }
}
