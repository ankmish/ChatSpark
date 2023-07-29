package com.chat.messenger.controllers;

import com.chat.messenger.dto.Message;
import com.chat.messenger.dto.MessageDTO;
import com.chat.messenger.dto.User;
import com.chat.messenger.dto.UserDTO;
import com.chat.messenger.services.MessageService;
import com.chat.messenger.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/create/user")
    public String createUser(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String passcode = userDTO.getPasscode();
        boolean created = userService.createUser(username, passcode);
        if (created) {
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User already exists\"}";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String passcode = userDTO.getPasscode();
        User user = userService.getUser(username);
        if (user != null && user.getPasscode().equals(passcode)) {
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"Invalid username or passcode\"}";
        }
    }

    @GetMapping("/get/users")
    public String getUsers(@RequestParam String username) {
        if (isUserLoggedIn(username)) {
            List<String> usernames = userService.getAllUsernames();
            return "{\"status\":\"success\", \"data\":" + toJson(usernames) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    @GetMapping("/get/unread")
    public String getUnreadMessages(@RequestParam String username) {
        // Check if the user is logged in
        if (isUserLoggedIn(username)) {
            List<Message> unreadMessages = messageService.getUnreadMessages(username);
            return "{\"status\":\"success\", \"message\":\"You have message(s)\", \"data\":" +
                    toJson(unreadMessages) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    @PostMapping("/send/text/user")
    public String sendMessage(@RequestBody MessageDTO messageDTO) {
        String sender = messageDTO.getFrom();
        String recipient = messageDTO.getTo();
        String text = messageDTO.getText();
        messageService.sendMessage(sender, recipient, text);
        return "{\"status\":\"success\"}";
    }

    @GetMapping("/get/history")
    public String getChatHistory(@RequestParam String user, @RequestParam String friend) {
        // Check if the user is logged in
        if (isUserLoggedIn(user)) {
            List<Message> chatHistory = messageService.getChatHistory(user, friend);
            return "{\"status\":\"success\", \"data\":" + toJson(chatHistory) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    private boolean isUserLoggedIn(String username) {
        //assume that the user is logged in if the username is not null or empty.
        return username != null && !username.isEmpty();
    }
    private String toJson(Object data) {
        return new Gson().toJson(data);
    }
}

