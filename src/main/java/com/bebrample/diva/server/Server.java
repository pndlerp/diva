package com.bebrample.diva.server;

import com.bebrample.diva.servermember.ServerMember;
import com.bebrample.diva.channel.Channel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "servers")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Server {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<ServerMember> members;

    @OneToMany
    @JoinColumn(name = "channel_id")
    private List<Channel> channels;
}
