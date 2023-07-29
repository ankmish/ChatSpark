package com.chat.messenger.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String from;
    private String to;
    private String text;
}