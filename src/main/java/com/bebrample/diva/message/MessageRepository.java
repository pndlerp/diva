package com.bebrample.diva.message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChannelId(Long channelId);

    void deleteAllByChannelServerId(Long serverId);
}
