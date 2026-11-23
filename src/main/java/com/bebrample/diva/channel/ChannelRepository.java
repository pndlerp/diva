package com.bebrample.diva.channel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    void deleteAllByServerId(Long serverId);

    List<Channel> findAllByServerId(Long serverId);
}
