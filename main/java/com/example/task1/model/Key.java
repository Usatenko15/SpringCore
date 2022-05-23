package com.example.task1.model;

import java.util.Objects;

public class Key {
    public EntityName entityName;
    private long id;

    public Key(EntityName entityName, long id) {
        this.entityName = entityName;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return id == key.id && entityName == key.entityName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityName, id);
    }
}
