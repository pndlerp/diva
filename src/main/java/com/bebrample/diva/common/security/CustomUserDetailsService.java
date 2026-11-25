package com.bebrample.diva.common.security;

import com.bebrample.diva.user.User;
import com.bebrample.diva.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }
}
