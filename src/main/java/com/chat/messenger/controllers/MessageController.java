package com.chat.messenger.controllers;

import com.chat.messenger.dto.GroupMessageDTO;
import com.chat.messenger.dto.Message;
import com.chat.messenger.dto.MessageDTO;
import com.chat.messenger.dto.User;
import com.chat.messenger.dto.UserDTO;
import com.chat.messenger.services.MessageService;
import com.chat.messenger.services.UserService;
import com.chat.messenger.utils.ConverterUtil;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat-spark")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;
    private final Map<String, Boolean> loggedInUsers; // Store logged-in users


    @Autowired
    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
        this.loggedInUsers = new HashMap<>();
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
            loggedInUsers.put(username, true);
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"Invalid username or passcode\"}";
        }
    }

    @GetMapping("/get/users")
    public String getUsers(@RequestParam String username) {
        if (isLoggedIn(username)) {
            List<String> usernames = userService.getAllUsernames();
            return "{\"status\":\"success\", \"data\":" + ConverterUtil.toJson(usernames) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    @GetMapping("/get/unread")
    public String getUnreadMessages(@RequestParam String username) {
        // Check if the user is logged in
        if (isLoggedIn(username)) {
            List<Message> unreadMessages = messageService.getUnreadMessages(username);
            return "{\"status\":\"success\", \"message\":\"You have message(s)\", \"data\":" +
                    ConverterUtil.toJson(unreadMessages) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    @PostMapping("/send/text/user")
    public String sendMessage(@RequestBody MessageDTO messageDTO) {
        String sender = messageDTO.getFrom();
        // Check if the sender is logged in
        if (!isLoggedIn(sender)) {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }

        String recipient = messageDTO.getTo();
        String text = messageDTO.getText();
        messageService.sendMessage(sender, recipient, text);
        return "{\"status\":\"success\"}";
    }
    @GetMapping("/get/history")
    public String getChatHistory(@RequestParam String user, @RequestParam String friend) {
        // Check if the user is logged in
        if (isLoggedIn(user)) {
            List<Message> chatHistory = messageService.getChatHistory(user, friend);
            return "{\"status\":\"success\", \"data\":" + ConverterUtil.toJson(chatHistory) + "}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    @PostMapping("/logout")
    public String logoutUser(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        // Check if the user is logged in before logging out
        if (isLoggedIn(username)) {
            loggedInUsers.remove(username);
            return "{\"status\":\"success\"}";
        } else {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }
    }

    //  to send a message to a group of users
    @PostMapping("/send/text/group")
    public String sendGroupMessage(@RequestBody GroupMessageDTO groupMessageDTO) {
        String sender = groupMessageDTO.getSender();
        if (!isLoggedIn(sender)) {
            return "{\"status\":\"failure\", \"message\":\"User not logged in\"}";
        }

        List<String> recipients = groupMessageDTO.getRecipients();
        String text = groupMessageDTO.getText();
        messageService.sendGroupMessage(sender, recipients, text);
        return "{\"status\":\"success\"}";
    }

    private boolean isLoggedIn(String username) {
        return loggedInUsers.containsKey(username) && loggedInUsers.get(username);
    }
}

