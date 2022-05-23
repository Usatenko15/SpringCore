package com.example.task1.service;

import com.example.task1.DAO.EventDAO;
import com.example.task1.DAO.TicketDAO;
import com.example.task1.DAO.UserDAO;
import com.example.task1.model.EventImpl;
import com.example.task1.model.TicketImpl;
import com.example.task1.model.UserImpl;
import model.Event;
import model.Ticket;
import model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketServiceTest {

    @Autowired
    TicketService ticketService;
    @MockBean
    TicketDAO ticketDAO;
    @MockBean
    UserDAO userDAO;
    @MockBean
    EventDAO eventDAO;


    @Test
    void bookTicket() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        ticketService.bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        Mockito.verify(ticketDAO, Mockito.times(1)).bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        assertTrue(event.getPlaces().get(2));
    }

    @Test
    void bookTicketWhereNoUserWithSuchId() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
        Ticket ticket = ticketService.bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(eventDAO, Mockito.times(0)).getEventById(event.getId());
        Mockito.verify(ticketDAO, Mockito.times(0)).bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        assertFalse(event.getPlaces().get(2));
        assertNull(ticket);
    }

    @Test
    void bookTicketWhereNoEventWithSuchId() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(null);
        Ticket ticket = ticketService.bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(eventDAO, Mockito.times(1)).getEventById(event.getId());
        Mockito.verify(ticketDAO, Mockito.times(0)).bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        assertFalse(event.getPlaces().get(2));
        assertNull(ticket);
    }

    @Test
    void bookTicketWherePlaceUnderLimit() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        Ticket ticket = ticketService.bookTicket(user.getId(), event.getId(),
                35, Ticket.Category.STANDARD);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(eventDAO, Mockito.times(1)).getEventById(event.getId());
        Mockito.verify(ticketDAO, Mockito.times(0)).bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        assertFalse(event.getPlaces().get(2));
        assertNull(ticket);
    }

    @Test
    void bookTicketWherePlaceIsTaken() {
        Event event = new EventImpl("Event", new Date());
        event.getPlaces().set(2, true);
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        Ticket ticket = ticketService.bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        Mockito.verify(userDAO, Mockito.times(1)).getUserById(user.getId());
        Mockito.verify(eventDAO, Mockito.times(2)).getEventById(event.getId());
        Mockito.verify(ticketDAO, Mockito.times(0)).bookTicket(user.getId(), event.getId(),
                3, Ticket.Category.STANDARD);
        assertNull(ticket);
    }

    @Test
    void getBookedTickets() {
        Event event = new EventImpl("Event", new Date());
        ticketService.getBookedTickets(event);
        Mockito.verify(ticketDAO, Mockito.times(1)).getBookedTickets(event);
    }

    @Test
    void testGetBookedTickets() {
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        ticketService.getBookedTickets(user);
        Mockito.verify(ticketDAO, Mockito.times(1)).getBookedTickets(user);
    }

    @Test
    void cancelTicket() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Ticket ticket = new TicketImpl(event.getId(), user.getId(), Ticket.Category.STANDARD, 3);
        Mockito.when(ticketDAO.getTicketById(ticket.getId())).thenReturn(ticket);
        Mockito.when(eventDAO.getEventById(event.getId())).thenReturn(event);
        boolean isCanceled = ticketService.cancelTicket(ticket.getId());
        Mockito.verify(ticketDAO, Mockito.times(1)).cancelTicket(ticket.getId());
        assertFalse(event.getPlaces().get(2));
        assertTrue(isCanceled);
    }

    @Test
    void cancelTicketWhereNoTicketWithSuchId() {
        Event event = new EventImpl("Event", new Date());
        User user = new UserImpl("Nikita", "nikita@gmail.com");
        Ticket ticket = new TicketImpl(event.getId(), user.getId(), Ticket.Category.STANDARD, 3);
        Mockito.when(ticketDAO.getTicketById(ticket.getId())).thenReturn(null);
        boolean isCanceled = ticketService.cancelTicket(ticket.getId());
        Mockito.verify(ticketDAO, Mockito.times(0)).cancelTicket(ticket.getId());
        assertFalse(isCanceled);
    }
}