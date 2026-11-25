package com.bebrample.diva.user;

import com.bebrample.diva.channel.ChannelResponse;
import com.bebrample.diva.common.security.CustomUserDetails;
import com.bebrample.diva.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/auth/login")
    public ResponseEntity<Void> loginToAccount(@Valid @RequestBody LoginRequest request){
        ResponseCookie cookie = authService.login(request.mail(), request.password());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> registerAccount(@Valid @RequestBody RegisterRequest request){
        authService.register(request.username(), request.mail(), request.password());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public UserResponse getMe(@AuthenticationPrincipal CustomUserDetails user){
        return userService.findById(user.getId());
    }
    @PostMapping("/message/send")
    public void sendMessage(@AuthenticationPrincipal CustomUserDetails user,
                            @RequestBody MessageRequest message){
        userService.sendMessage(user.getId(), message.text(), message.channelId());
    }
    @GetMapping("/channel/{channelId}/messages/")
    public List<MessageResponse> getMessagesByChannel(@AuthenticationPrincipal CustomUserDetails user,
                                                      @PathVariable Long channelId){
        return userService.getChannelMessages(user.getId(), channelId);
    }

    @GetMapping("/user/servers")
    public UserServers getUserServers(@AuthenticationPrincipal CustomUserDetails user){
        return userService.getUserServers(user.getId());
    }

    @PostMapping("/user/invite")
    public void joinByCode(@AuthenticationPrincipal CustomUserDetails user, @RequestBody String link){
        userService.joinByInviteCode(user.getId(), link);
    }

    @GetMapping("/{serverId}/channels")
    public List<ChannelResponse> getChannels(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable Long serverId){
        return userService.getChannels(userDetails.getId(), serverId);
    }
}
