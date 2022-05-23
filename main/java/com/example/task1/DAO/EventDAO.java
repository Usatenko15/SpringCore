package com.example.task1.DAO;

import com.example.task1.model.EntityName;
import com.example.task1.model.Key;
import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class EventDAO {
    private Map<Key, Object> dataBase;

    @Autowired
    public void setDataBase(Map<Key, Object> dataBase) {
        this.dataBase = dataBase;
    }


    public Event getEventById(long eventId) {
        Key key = new Key(EntityName.EVENT, eventId);
        return (Event) dataBase.get(key);
    }

    public List<Event> getEventsByTitle(String title) {
        List<Event> events = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.EVENT)) {
                Event event = (Event) o;
                if (event.getTitle().equals(title)) {
                    events.add(event);
                }
            }
        });
        return events;
    }

    public List<Event> getEventsForDay(Date day) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        List<Event> events = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.EVENT)) {
                Event event = (Event) o;
                if (simpleDateFormat.format(event.getDate()).equals(simpleDateFormat.format(day))) {
                    events.add(event);
                }
            }
        });
        return events;
    }

    public Event createEvent(Event event) {
        Key key = new Key(EntityName.EVENT, event.getId());
        dataBase.put(key, event);
        return event;
    }

    public Event updateEvent(Event event) {
        Event updatedEvent = getEventById(event.getId());
        updatedEvent.setTitle(event.getTitle());
        updatedEvent.setDate(event.getDate());
        return updatedEvent;
    }

    public void deleteEvent(long eventId) {
        Key key = new Key(EntityName.EVENT, eventId);
        dataBase.remove(key);
    }
}
