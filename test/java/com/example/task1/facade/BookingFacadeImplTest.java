package com.example.task1.facade;

import com.example.task1.model.EventImpl;
import com.example.task1.model.UserImpl;
import model.Event;
import model.Ticket;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingFacadeImplTest {

    @Autowired
    BookingFacadeImpl bookingFacade;

    @Test
    void userLifeCycle() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        User updatedUser = new UserImpl("Nikita", "nikita@gmail.com");
        updatedUser.setId(user.getId());
        bookingFacade.createUser(user);
        assertEquals(bookingFacade.getUserByEmail("nikita@gmail.com"), updatedUser);
        assertEquals(bookingFacade.getUserById(user.getId()), updatedUser);

        updatedUser.setName("NotNikita");
        bookingFacade.updateUser(updatedUser);
        assertEquals(bookingFacade.getUserById(user.getId()), updatedUser);

        updatedUser.setEmail("nikita@gmail.com");
        bookingFacade.updateUser(updatedUser);
        assertEquals(bookingFacade.getUserById(user.getId()), updatedUser);

        bookingFacade.deleteUser(user.getId());
        assertNull(bookingFacade.getUserById(user.getId()));
    }

    @Test
    void eventLifeCycle() {
        Event event = new EventImpl("EventHere", new Date());
        Event updatedEvent = new EventImpl("EventHere", new Date());
        updatedEvent.setId(event.getId());
        bookingFacade.createEvent(event);
        assertEquals(bookingFacade.getEventById(event.getId()), updatedEvent);

        updatedEvent.setTitle("EventThere");
        bookingFacade.updateEvent(updatedEvent);
        assertEquals(bookingFacade.getEventById(event.getId()), updatedEvent);

        updatedEvent.setDate(new Date(234));
        bookingFacade.updateEvent(updatedEvent);
        assertEquals(bookingFacade.getEventById(event.getId()), updatedEvent);

        bookingFacade.deleteEvent(event.getId());
        assertNull(bookingFacade.getEventById(event.getId()));
    }

    @Test
    void ticketLifeCycle() {
        User user = new UserImpl("Nikita", "nik12ita@gmail.com");
        Event event = new EventImpl("EventHere", new Date());
        bookingFacade.createUser(user);
        bookingFacade.createEvent(event);
        Ticket createdTicket = bookingFacade.bookTicket(user.getId(), event.getId(), 2, Ticket.Category.STANDARD);
        assertEquals(bookingFacade.getBookedTickets(user).get(0), createdTicket);
        assertEquals(bookingFacade.getBookedTickets(event).get(0), createdTicket);

        bookingFacade.cancelTicket(createdTicket.getId());
        assertTrue(bookingFacade.getBookedTickets(user).isEmpty());
        assertTrue(bookingFacade.getBookedTickets(event).isEmpty());
    }
}