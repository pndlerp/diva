package com.bebrample.diva.server;

import com.bebrample.diva.common.security.CustomUserDetails;
import com.bebrample.diva.servermember.ServerMembersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;
    @GetMapping("/{serverId}/users")
    public ServerMembersResponse getAllUsersFromServer(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @PathVariable Long serverId){
        return serverService.getUsersByServer(userDetails.getId(), serverId);
    }

    @PostMapping("/create")
    public Server createServer(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestBody String name){
        return serverService.createServer(userDetails.getId(), name);
    }
    @PostMapping("/{serverId}/channel/create")
    public void createChannel(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable Long serverId, String name){
         serverService.createChannel(userDetails.getId(), serverId, name);
    }

    @PostMapping("/{serverId}/invite")
    public String createInviteCode(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable Long serverId){
        return serverService.createInviteCode(userDetails.getId(), serverId);
    }

    @GetMapping("/{serverId}/invite")
    public List<String> getInvites(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable Long serverId){
        return serverService.getInviteCodes(userDetails.getId(), serverId);
    }

    @DeleteMapping("/{serverId}")
    public void deleteServer(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable Long serverId){
        serverService.deleteServer(userDetails.getId(), serverId);
    }

}
