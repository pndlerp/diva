package com.bebrample.diva.message;

import com.bebrample.diva.user.User;
import com.bebrample.diva.channel.Channel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue
    private Long Id;

    private String text;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @Column(name = "date")
    private LocalDateTime localDateTime = LocalDateTime.now();
}
