package com.bebrample.diva.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MessageResponse(Long senderId,
                              Long channelId,
                              String text,
                              LocalDateTime date) {
}
