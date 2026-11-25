package com.bebrample.diva.user;

import com.bebrample.diva.channel.Channel;
import com.bebrample.diva.channel.ChannelRepository;
import com.bebrample.diva.channel.ChannelResponse;
import com.bebrample.diva.common.exception.DuplicateResourcesException;
import com.bebrample.diva.common.exception.NotEnoughAuthoritiesException;
import com.bebrample.diva.common.exception.ResourcesNotFoundException;
import com.bebrample.diva.invite.Invite;
import com.bebrample.diva.invite.InviteRepository;
import com.bebrample.diva.message.Message;
import com.bebrample.diva.message.MessageRepository;
import com.bebrample.diva.server.Server;
import com.bebrample.diva.server.ServerRepository;
import com.bebrample.diva.servermember.Authority;
import com.bebrample.diva.servermember.ServerMember;
import com.bebrample.diva.servermember.ServerMemberRepository;
import com.bebrample.diva.user.dto.MessageResponse;
import com.bebrample.diva.user.dto.UserResponse;
import com.bebrample.diva.user.dto.UserServers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ServerMemberRepository serverMemberRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final InviteRepository inviteRepository;
    private final ServerRepository serverRepository;

    public UserResponse findByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourcesNotFoundException("Username not found"));
        return UserResponse.builder()
                .email(user.getMail())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }

    public UserResponse findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Username not found"));
        return UserResponse.builder()
                .email(user.getMail())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .build();
    }

    public UserServers getUserServers(Long userId){
        List<ServerMember> serverMemberList = serverMemberRepository.findAllByMemberId(userId);
        return new UserServers(userId, serverMemberList.stream()
                .map(item -> item.getServer().getId()).toList());
    }

    public void sendMessage(Long userId, String text, Long channelId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourcesNotFoundException("User Not found"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourcesNotFoundException("Channel doesnt exist"));
        Message message = new Message(text, user, channel);

        messageRepository.save(message);
    }

    public List<ChannelResponse> getChannels(Long userId, Long serverId){
        if(!haveMembership(userId, serverId)) {
            throw new NotEnoughAuthoritiesException("You are not member of server");
        }
        List<Channel> channels = channelRepository.findAllByServerId(serverId);

        return channels.stream()
                .map(channel -> new ChannelResponse(
                        channel.getId(), channel.getName()))
                .toList();

    }

    public List<MessageResponse> getChannelMessages(Long userId, Long channelId){
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourcesNotFoundException("Channel not found"));
        if(!haveMembership(userId, channel.getServer().getId())) {
            throw new NotEnoughAuthoritiesException("You are not member of server");
        }
            List<Message> messages = messageRepository.findAllByChannelId(channelId);
            return messages.stream().map(message ->
                    new MessageResponse(
                            message.getSender().getId(),
                            message.getChannel().getId(),
                            message.getText(),
                            message.getLocalDateTime())).toList();

    }

    public void joinByInviteCode(Long userId, String link){
        Invite invite = inviteRepository.findByLink(link)
                .orElseThrow( () -> new ResourcesNotFoundException("Invite link is no longer accessible"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourcesNotFoundException("User not found"));
        Server server = serverRepository.findById(invite.getServer().getId())
                .orElseThrow(() -> new ResourcesNotFoundException("Server not found"));

        if(serverMemberRepository.existsByMemberIdAndServerId(userId, server.getId())){
            throw new DuplicateResourcesException("You already joined to this server");
        }
        ServerMember serverMember = new ServerMember(user, server, Authority.MEMBER);

        serverMemberRepository.save(serverMember);
    }

    private boolean haveMembership(Long userId, Long serverId){
        return serverMemberRepository.existsByMemberIdAndServerId(userId, serverId);
    }
}
