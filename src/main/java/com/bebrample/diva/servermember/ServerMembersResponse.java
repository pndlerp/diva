package com.bebrample.diva.servermember;

import java.util.List;

public record ServerMembersResponse(Long serverId,
                                    List<Long> userIds) {
}
