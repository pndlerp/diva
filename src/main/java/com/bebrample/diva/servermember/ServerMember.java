package com.bebrample.diva.servermember;

import com.bebrample.diva.user.User;
import com.bebrample.diva.server.Server;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "servers_members")
public class ServerMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;
    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public ServerMember(User member, Server server, Authority authority) {
        this.member = member;
        this.server = server;
        this.authority = authority;
    }
}
