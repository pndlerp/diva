package com.bebrample.diva.channel;

import com.bebrample.diva.server.Server;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Server server;

    private String name;

    public Channel(Server server, String name) {
        this.server = server;
        this.name = name;
    }


}
