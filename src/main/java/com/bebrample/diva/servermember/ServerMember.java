package com.bebrample.diva.servermember;

import com.bebrample.diva.user.User;
import com.bebrample.diva.server.Server;
import jakarta.persistence.*;

@Entity
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

}
