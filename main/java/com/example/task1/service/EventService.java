package com.example.task1.service;

import com.example.task1.DAO.EventDAO;
import model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    EventDAO eventDAO;

    public Event getEventById(long eventId) {
        if (eventDAO.getEventById(eventId) == null) {
            logger.error("No such event with id: " + eventId);
        }
        return eventDAO.getEventById(eventId);
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsByTitle(String title) {
        return eventDAO.getEventsByTitle(title);
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsForDay(Date day) {
        return eventDAO.getEventsForDay(day);
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    /**
     * Updates event using given data.
     *
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event) {
        if (eventDAO.getEventById(event.getId()) == null) {
            logger.error("No such event with id: " + event.getId());
            return null;
        }
        return eventDAO.updateEvent(event);
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId) {
        if (eventDAO.getEventById(eventId) == null) {
            logger.error("No such event with id: " + eventId);
            return false;
        }
        eventDAO.deleteEvent(eventId);
        return true;
    }
}
