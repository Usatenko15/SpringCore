package com.example.task1.model;


import model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventImpl implements Event {
    private static long count;
    private List<Boolean> places = new ArrayList();


    private Long id;
    private String title;
    private Date date;


    {
        for (int i = 0; i < 29; i++) {
            places.add(false);
        }
    }

    public List<Boolean> getPlaces() {
        return places;
    }

    public void setPlaces(List<Boolean> places) {
        this.places = places;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EventImpl(String title, Date date) {
        this.id = count++;
        this.title = title;
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventImpl event = (EventImpl) o;
        return Objects.equals(id, event.id) && Objects.equals(title, event.title) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date);
    }

}
