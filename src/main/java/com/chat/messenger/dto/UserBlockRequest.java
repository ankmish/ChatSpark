package com.chat.messenger.dto;

import lombok.Data;

@Data
public class UserBlockRequest {
    String blockedBy;
    String blockedTo;
}
