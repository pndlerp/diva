package com.bebrample.diva.user;

import com.bebrample.diva.common.exception.DuplicateResourcesException;
import com.bebrample.diva.common.security.CookieService;
import com.bebrample.diva.common.security.CustomUserDetails;
import com.bebrample.diva.common.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CookieService cookieService;

    @Transactional
    public void register(String username, String mail, String password){
        if(userRepository.existsByMail(mail)) throw new DuplicateResourcesException("User with this email already registered");
        if(userRepository.existsByUsername(username)) throw new DuplicateResourcesException("User with this name already registered");

        String hashedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .username(username)
                .nickname(username)
                .mail(mail)
                .password(hashedPassword)
                .build();

        userRepository.save(user);
    }

    public ResponseCookie login(String email, String password){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return cookieService.createCookie(token);
    }

//    private User toUser(RegisterRequest request){
//
//    }
}
