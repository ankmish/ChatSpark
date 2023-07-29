package com.chat.messenger.services;

import com.chat.messenger.dao.MessageDao;
import com.chat.messenger.dto.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private MessageDao messageDao;

    public UserService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public boolean createUser(String username, String passcode) {
        User user = new User(username, passcode, new ArrayList<>());
        return messageDao.addUser(user);
    }

    public User getUser(String username) {
        return messageDao.getUser(username);
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        for (User user : messageDao.getUsers()) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
}

