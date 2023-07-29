package com.chat.messenger.dto;

import java.util.List;
import lombok.Data;

@Data
public class GroupMessageDTO {
    private String sender;
    private List<String> recipients;
    private String text;

    // Constructors, getters, and setters
}
