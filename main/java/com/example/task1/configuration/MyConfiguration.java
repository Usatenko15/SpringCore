package com.example.task1.configuration;

import com.example.task1.model.EntityName;
import com.example.task1.model.EventImpl;
import com.example.task1.model.Key;
import com.example.task1.model.UserImpl;
import model.Event;
import model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class MyConfiguration {

    @Bean
    public Map<Key, Object> db() {
        HashMap<Key, Object> keyObjectHashMap = new HashMap<>();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(new UserImpl("Vasya" + i, "sdsd" + i + "@fdf"));
        }
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(new EventImpl("Event" + i, new Date()));
        }
        for (int i = 0; i < 5; i++) {
            keyObjectHashMap.put(new Key(EntityName.USER, users.get(i).getId()), users.get(i));
            keyObjectHashMap.put(new Key(EntityName.EVENT, events.get(i).getId()), events.get(i));

        }
        return keyObjectHashMap;
    }
}

