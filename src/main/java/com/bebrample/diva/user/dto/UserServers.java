package com.bebrample.diva.user.dto;

import java.util.List;

public record UserServers(Long userId,
                          List<Long> serverIds) {
}
