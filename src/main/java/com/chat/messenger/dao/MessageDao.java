package com.chat.messenger.dao;

import com.chat.messenger.dto.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao {
    private Map<String, User> users;

    private Map<String, List<String> > blockRelationMap;

    public MessageDao() {
        this.users = new HashMap<>();
        this.blockRelationMap = new HashMap<>();
    }

    public boolean blockUser(String blockerName, String blockedToName) {
        if(!blockRelationMap.containsKey(blockerName)) {
            List<String> arrList = new ArrayList<>();
            arrList.add(blockedToName);
            blockRelationMap.put(blockerName, arrList);
        }
        else {
            List<String> arrList = blockRelationMap.get(blockerName);
            if(!arrList.contains(blockedToName)) {
                arrList.add(blockedToName);
            }
            blockRelationMap.put(blockerName, arrList);
        }

        if(!blockRelationMap.containsKey(blockedToName)) {
            List<String> arrList = new ArrayList<>();
            arrList.add(blockerName);
            blockRelationMap.put(blockedToName, arrList);
        }
        else {
            List<String> arrList = blockRelationMap.get(blockedToName);
            if(!arrList.contains(blockerName)) {
                arrList.add(blockerName);
            }
            blockRelationMap.put(blockedToName, arrList);
        }
        return true;
    }

    public boolean addUser(User user) {
        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public List<String> getBlockListForUser(String userName) {
        if (blockRelationMap.containsKey(userName)) {
            return blockRelationMap.get(userName);

        }
        return new ArrayList<>();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}

