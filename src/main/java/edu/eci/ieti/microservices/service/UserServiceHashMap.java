package edu.eci.ieti.microservices.service;

import edu.eci.ieti.microservices.data.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceHashMap implements UserService{

    private final HashMap<String, User> usersMap = new HashMap<>();
    @Override
    public User create(User user) {
        usersMap.put(user.getId(),user);
        return user;
    }

    @Override
    public User findById(String id) {
        if(usersMap.containsKey(id))
        {
            return usersMap.get(id);
        }
        return null;
    }

    @Override
    public List<User> all() {
        List<User> userList = new ArrayList<>();
        for(String id: usersMap.keySet()){
            userList.add(usersMap.get(id));
        }
        return userList;
    }

    @Override
    public void deleteById(String id) {
        usersMap.remove(id);

    }

    @Override
    public User update(User user, String userId) {
        return usersMap.put(userId,user);
    }
}
