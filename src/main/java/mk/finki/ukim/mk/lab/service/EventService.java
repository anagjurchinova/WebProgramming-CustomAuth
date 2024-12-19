package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;

import java.util.List;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text);
    List<Event> searchByRating(String popularityScore);
    public void delete(Long id);
    public Event searchById(Long id);
    public Event editEvent(Long id, String name, String description, Double popularityScore, Location location);
    public Event addNewEvent(String name, String description, double popularityScore, Location location);
    public List<Event> findEventAtLocation(Long id);
    public List<Event> searchByLocation(String location);
}
