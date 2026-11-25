package com.bebrample.diva.user;

import com.bebrample.diva.servermember.ServerMember;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    private String nickname;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "password", nullable = false)
    private String password;

}
