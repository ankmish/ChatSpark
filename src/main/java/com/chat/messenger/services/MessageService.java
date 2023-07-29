package com.chat.messenger.services;

import com.chat.messenger.dao.MessageDao;
import com.chat.messenger.dto.Message;
import com.chat.messenger.dto.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private MessageDao messageDao;

    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public List<Message> getUnreadMessages(String username) {
        User user = messageDao.getUser(username);
        if (user == null) {
            return new ArrayList<>();
        }
        List<Message> unreadMessages = new ArrayList<>();
        for (Message message : user.getMessages()) {
            if (!message.isRead()) {
                unreadMessages.add(message);
                message.setRead(true);
            }
        }
        return unreadMessages;
    }

    public void sendMessage(String sender, String recipient, String text) {
        User user = messageDao.getUser(recipient);
        if (user != null) {
            user.getMessages().add(new Message(sender, recipient, text, false));
        }
    }

    public List<Message> getChatHistory(String user1, String user2) {
        User sender = messageDao.getUser(user1);
        User receiver = messageDao.getUser(user2);
        if (sender == null || receiver == null) {
            return new ArrayList<>();
        }
        List<Message> chatHistory = new ArrayList<>();
        for (Message message : sender.getMessages()) {
            if (message.getSender().equals(user2) || message.getRecipient().equals(user2)) {
                chatHistory.add(message);
            }
        }
        return chatHistory;
    }

    public void sendGroupMessage(String sender, List<String> recipients, String text) {
        for (String recipient : recipients) {
            User user = messageDao.getUser(recipient);
            if (user != null) {
                user.getMessages().add(new Message(sender, recipient, text, false));
            }
        }
    }
}
