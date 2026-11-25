package com.bebrample.diva.servermember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerMemberRepository extends JpaRepository<ServerMember, Long> {
    List<ServerMember> findAllByServerId(Long serverId);

    List<ServerMember> findAllByMemberId(Long memberId);
    ServerMember findServerMemberByMemberIdAndServerId(Long memberId, Long serverId);

    boolean existsByMemberIdAndServerId(Long memberId, Long serverId);
    void deleteAllByServerId(Long serverId);
}
