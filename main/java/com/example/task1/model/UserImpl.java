package com.example.task1.model;

import lombok.Getter;
import lombok.Setter;
import model.User;

@Getter
@Setter
public class UserImpl implements User {
    private static long count;
    private Long id;
    private String name;
    private String email;

    public UserImpl(String name, String email) {
        this.id = count++;
        this.name = name;
        this.email = email;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
