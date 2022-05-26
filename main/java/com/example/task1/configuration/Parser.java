package com.example.task1.configuration;

import com.example.task1.model.*;
import model.Event;
import model.Ticket;
import model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Parser {
    public HashMap<Key, Object> parse(String fileName){

        HashMap<Key, Object> map = new HashMap<>();

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(fileName)){
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray jsonUserArray = (JSONArray) jsonObject.get("users");
            for(Object it: jsonUserArray) {
                JSONObject jsonUser = (JSONObject) it;
                String name = (String) jsonUser.get("name");
                String email = (String) jsonUser.get("email");

                User user = new UserImpl(name, email);
                map.put(new Key(EntityName.USER, user.getId()), user);
            }

            JSONArray jsonEventArray = (JSONArray) jsonObject.get("events");
            for(Object it: jsonEventArray) {
                JSONObject jsonEvent = (JSONObject) it;
                String title = (String) jsonEvent.get("title");

                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String dateInString = (String) jsonEvent.get("date");
                Date date = simpleDateFormat.parse(dateInString);
                Event event = new EventImpl(title, date);
                map.put(new Key(EntityName.EVENT, event.getId()), event);
            }

            JSONArray jsonTicketArray = (JSONArray) jsonObject.get("tickets");
            for(Object it: jsonTicketArray) {
                JSONObject jsonTicket = (JSONObject) it;
                Long eventId = (Long) jsonTicket.get("eventId");
                Long userId = (Long) jsonTicket.get("userId");
                String category = (String) jsonTicket.get("category");
                long place = (Long) jsonTicket.get("place");

                Ticket ticket = new TicketImpl(eventId, userId, Ticket.Category.valueOf(category), (int) place);
                map.put(new Key(EntityName.EVENT, ticket.getId()), ticket);
            }
            return map;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
