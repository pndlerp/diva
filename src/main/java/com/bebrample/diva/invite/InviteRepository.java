package com.bebrample.diva.invite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    boolean existsByLink(String link);
    Optional<Invite> findByLink(String link);

    List<Invite> findByServerId(Long serverId);


    void deleteAllByServerId(Long serverId);
}
