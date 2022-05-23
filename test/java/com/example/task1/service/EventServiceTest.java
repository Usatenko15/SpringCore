package com.example.task1.service;

import com.example.task1.DAO.EventDAO;
import com.example.task1.model.EventImpl;
import model.Event;
import model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EventServiceTest {

    @Autowired
    EventService eventService;

    @MockBean
    EventDAO eventDAO;

    @Test
    void getEventById() {
        eventService.getEventById(1);
        Mockito.verify(eventDAO, Mockito.times(2)).getEventById(1);
    }

    @Test
    void getEventByIdWhereNoEventWithSuchId() {
        Mockito.when(eventDAO.getEventById(1)).thenReturn(null);
        Event event = eventService.getEventById(1);
        Mockito.verify(eventDAO, Mockito.times(2)).getEventById(1);
        assertNull(event);
    }

    @Test
    void getEventsByTitle() {
        Event event = new EventImpl("dsds", new Date());
        eventService.getEventsByTitle(event.getTitle());
        Mockito.verify(eventDAO, Mockito.times(1)).getEventsByTitle(event.getTitle());
    }

    @Test
    void getEventsForDay() {
        Event event = new EventImpl("dsds", new Date());
        eventService.getEventsForDay(event.getDate());
        Mockito.verify(eventDAO, Mockito.times(1)).getEventsForDay(event.getDate());
    }

    @Test
    void createEvent() {
        Event event = new EventImpl("dsds", new Date());
        eventService.createEvent(event);
        Mockito.verify(eventDAO, Mockito.times(1)).createEvent(event);
    }

    @Test
    void updateEvent() {
        Event event = new EventImpl("dsds", new Date());
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        eventService.updateEvent(event);
        Mockito.verify(eventDAO, Mockito.times(1)).updateEvent(event);
    }

    @Test
    void updateEventWhereNoEventWithSuchId() {
        Event event = new EventImpl("dsds", new Date());
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(null);
        Event expectedEvent = eventService.updateEvent(event);
        Mockito.verify(eventDAO, Mockito.times(0)).updateEvent(event);
        assertNull(expectedEvent);
    }

    @Test
    void deleteEvent() {
        Event event = new EventImpl("dsds", new Date());
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        boolean isDelete = eventService.deleteEvent(event.getId());
        Mockito.verify(eventDAO, Mockito.times(1)).deleteEvent(event.getId());
        assertTrue(isDelete);
    }

    @Test
    void deleteEventWhereNoEventWithSuchId() {
        Event event = new EventImpl("dsds", new Date());
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(null);
        boolean isDelete = eventService.deleteEvent(event.getId());
        Mockito.verify(eventDAO, Mockito.times(0)).deleteEvent(event.getId());
        assertFalse(isDelete);
    }
}