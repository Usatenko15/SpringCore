package com.example.task1.DAO;

import com.example.task1.configuration.DB;
import com.example.task1.model.EntityName;
import com.example.task1.model.Key;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
    private Map<Key, Object> dataBase;

    @Autowired
    public void setDataBase(DB db) {
        this.dataBase = db.getDB();
    }


    public User getUserById(long userId) {
        Key key = new Key(EntityName.USER, userId);
        return (User) dataBase.get(key);
    }

    public User getUserByEmail(String email) {
        List<User> users = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.USER)) {
                User user = (User) o;
                if (user.getEmail().equals(email)) {
                    users.add(user);
                }
            }
        });
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    public List<User> getUsersByName(String name) {
        List<User> users = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.USER)) {
                User user = (User) o;
                if (user.getName().equals(name)) {
                    users.add(user);
                }
            }
        });
        return users;
    }

    public User createUser(User user) {
        Key key = new Key(EntityName.USER, user.getId());
        dataBase.put(key, user);
        return user;
    }

    public User updateUser(User user) {
        User updatedUser = getUserById(user.getId());
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        return updatedUser;
    }

    public void deleteUser(long userId) {
        Key key = new Key(EntityName.USER, userId);
        dataBase.remove(key);
    }
}
