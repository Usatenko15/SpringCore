package com.example.task1.service;

import com.example.task1.DAO.EventDAO;
import com.example.task1.DAO.TicketDAO;
import com.example.task1.DAO.UserDAO;
import model.Event;
import model.Ticket;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    UserDAO userDAO;

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws java.lang.IllegalStateException if this place has already been booked.
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (userDAO.getUserById(userId) == null) {
            logger.error("No such user with id: " + userId);
            return null;
        }
        if (eventDAO.getEventById(eventId) == null) {
            logger.error("No such event with id: " + eventId);
            return null;
        }
        if (place < 1 || place > 30) {
            logger.error("Place should be between 1 and 30");
            return null;
        }
        if (eventDAO.getEventById(eventId).getPlaces().get(place - 1)) {
            logger.error("The place " + place + " is already taken");
            return null;
        }
        eventDAO.getEventById(eventId).getPlaces().set(place - 1, true);
        return ticketDAO.bookTicket(userId, eventId, place, category);
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user) {
        return ticketDAO.getBookedTickets(user);
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event) {
        return ticketDAO.getBookedTickets(event);
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId) {
        if (ticketDAO.getTicketById(ticketId) == null) {
            logger.error("No such ticket with id: " + ticketId);
            return false;
        }
        long eventId = ticketDAO.getTicketById(ticketId).getEventId();
        int place = ticketDAO.getTicketById(ticketId).getPlace();
        eventDAO.getEventById(eventId).getPlaces().set(place - 1, false);
        ticketDAO.cancelTicket(ticketId);
        return true;
    }
}
