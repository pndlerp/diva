package com.bebrample.diva.server;

import jakarta.persistence.*;
import lombok.*;


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
    @Column(name = "name", nullable = false)
    private String name;
}
