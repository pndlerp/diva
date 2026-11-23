package com.bebrample.diva.user.dto;

public record MessageRequest(
                             String text,
                             Long channelId) {
}
