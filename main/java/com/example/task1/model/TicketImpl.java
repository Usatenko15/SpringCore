package com.example.task1.model;

import lombok.Getter;
import lombok.Setter;
import model.Ticket;

@Getter
@Setter
public class TicketImpl implements Ticket {
    private static long count;

    private Long id;
    private Long eventId;
    private Long userId;
    private Category category;
    private int place;

    public TicketImpl(Long eventId, Long userId, Category category, int place) {
        this.id = count++;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public long getEventId() {
        return eventId;
    }

    public long getUserId() {
        return userId;
    }

    public Category getCategory() {
        return category;
    }

    public int getPlace() {
        return place;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = id;
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
