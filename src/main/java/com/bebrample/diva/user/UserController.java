package com.bebrample.diva.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<Void> loginToAccount(@Valid @RequestBody LoginRequest request){
        ResponseCookie cookie = authService.login(request.mail(), request.password());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerAccount(@Valid @RequestBody RegisterRequest request){
        authService.register(request.username(), request.mail(), request.password());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public UserResponse getProfile(Principal principal){
        return userService.findByUsername(principal.getName());
    }
}
