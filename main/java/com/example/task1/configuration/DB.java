package com.example.task1.configuration;

import com.example.task1.model.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class DB {
    private Map<Key, Object> db;

    @Value("${fileName}")
    private String fileName;

    @PostConstruct
    private void postConstruct() {
        Parser parser = new Parser();
        db = parser.parse(fileName);
    }

    public Map<Key, Object> getDB() {
        return db;
    }
}
