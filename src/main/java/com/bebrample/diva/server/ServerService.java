package com.bebrample.diva.server;

import com.bebrample.diva.channel.Channel;
import com.bebrample.diva.channel.ChannelRepository;
import com.bebrample.diva.common.exception.NotEnoughAuthoritiesException;
import com.bebrample.diva.common.exception.ResourcesNotFoundException;
import com.bebrample.diva.invite.Invite;
import com.bebrample.diva.invite.InviteRepository;
import com.bebrample.diva.message.MessageRepository;
import com.bebrample.diva.servermember.Authority;
import com.bebrample.diva.servermember.ServerMember;
import com.bebrample.diva.servermember.ServerMemberRepository;
import com.bebrample.diva.servermember.ServerMembersResponse;
import com.bebrample.diva.user.User;
import com.bebrample.diva.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ServerMemberRepository serverMemberRepository;
    private final InviteRepository inviteRepository;
    private final MessageRepository messageRepository;
    @Transactional
    public Server createServer(Long userId, String serverName){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourcesNotFoundException("User not found"));

        Server server =  Server.builder().name(serverName).build();
        Channel channel = new Channel(server, "general");
        ServerMember serverMember = new ServerMember(user, server, Authority.OWNER);

        serverRepository.save(server);
        channelRepository.save(channel);
        serverMemberRepository.save(serverMember);

        return server;
    }

    @Transactional
    public void deleteServer(Long userId, Long serverId){
        if(!haveOwner(userId, serverId)) {
            throw new RuntimeException("Not enough authorities");
        }
        messageRepository.deleteAllByChannelServerId(serverId);
        inviteRepository.deleteAllByServerId(serverId);
        serverMemberRepository.deleteAllByServerId(serverId);
        channelRepository.deleteAllByServerId(serverId);
        serverRepository.deleteById(serverId);
    }

    public ServerMembersResponse getUsersByServer(Long userId, Long serverId){
        if(!haveMembership(userId, serverId)){
            throw new NotEnoughAuthoritiesException("You dont have access to this operation");
        }
        List<ServerMember> serverMembers = serverMemberRepository.findAllByServerId(serverId);
        return new ServerMembersResponse(serverId,
                serverMembers.stream().map(user -> user.getMember().getId()).toList());
    }

    @Transactional
    public void createChannel(Long userId, Long serverId, @RequestBody String name) {
        if(!haveOwner(userId, serverId)) {
            throw new RuntimeException("Not enough authorities");
        }
        Server server = Server.builder().id(serverId).build();
        Channel channel = new Channel(server, name);
        channelRepository.save(channel);

    }



    private boolean haveOwner(Long userId, Long serverId){
        ServerMember serverMember = serverMemberRepository
                .findServerMemberByMemberIdAndServerId(userId, serverId);

        return serverMember.getAuthority().equals(Authority.OWNER);
    }

    private boolean haveMembership(Long userId, Long serverId){
        return serverMemberRepository.existsByMemberIdAndServerId(userId, serverId);
    }

    public String createInviteCode(Long userId, Long serverId){
        if(!haveMembership(userId, serverId)){
            throw new NotEnoughAuthoritiesException("You dont have access to this operation");
        }
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ResourcesNotFoundException("Server not found"));
        Invite invite = null;
        boolean unique = false;
        while(!unique){
            invite = new Invite(server);
            unique = !(inviteRepository.existsByLink(invite.getLink()));
        }
        inviteRepository.save(invite);
        return invite.getLink();
    }

    public List<String> getInviteCodes(Long userId, Long serverId){
        if(haveOwner(userId, serverId)) {
            Server server = serverRepository.findById(serverId)
                    .orElseThrow(() -> new ResourcesNotFoundException("Server not found"));
            List<Invite> invites = inviteRepository.findByServerId(serverId);

            return invites
                    .stream()
                    .map(Invite::getLink)
                    .toList();
        }
        return null;
    }
}
