package com.chat.messenger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String sender;
    private String recipient;
    private String text;
    private boolean read;

}

