package com.example.task1.DAO;

import com.example.task1.model.EntityName;
import com.example.task1.model.Key;
import com.example.task1.model.TicketImpl;;
import model.Event;
import model.Ticket;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TicketDAO {
    private Map<Key, Object> dataBase;

    @Autowired
    public void setDataBase(Map<Key, Object> dataBase) {
        this.dataBase = dataBase;
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new TicketImpl(eventId, userId, category, place);
        Key key = new Key(EntityName.TICKET, ticket.getId());
        dataBase.put(key, ticket);
        return ticket;
    }

    public List<Ticket> getBookedTickets(User user) {
        List<Ticket> tickets = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.TICKET)) {
                Ticket ticket = (Ticket) o;
                if (ticket.getUserId() == user.getId()) {
                    tickets.add(ticket);
                }
            }
        });
        return tickets;
    }

    public List<Ticket> getBookedTickets(Event event) {
        List<Ticket> tickets = new ArrayList<>();
        dataBase.forEach((key, o) -> {
            if (key.entityName.equals(EntityName.TICKET)) {
                Ticket ticket = (Ticket) o;
                if (ticket.getEventId() == event.getId()) {
                    tickets.add(ticket);
                }
            }
        });
        return tickets;
    }

    public void cancelTicket(long ticketId) {
        Key key = new Key(EntityName.TICKET, ticketId);
        dataBase.remove(key);
    }

    public Ticket getTicketById(long ticketId) {
        Key key = new Key(EntityName.TICKET, ticketId);
        return (Ticket) dataBase.get(key);
    }
}
