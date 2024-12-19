package mk.finki.ukim.mk.lab.service;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }
    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text) {
        return eventRepository.findAllByNameContainingIgnoreCase(text);
    }

    @Override
    public List<Event> searchByRating(String popularityScore) {
        return eventRepository.findAllByPopularityScoreGreaterThanEqual(Double.parseDouble(popularityScore));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        eventRepository.deleteAllById(id);
    }

    @Override
    public Event searchById(Long id) {
        Optional<Event> event = eventRepository.findAllById(id);

        if(event.isPresent()) {
            return eventRepository.findAllById(id).get();
        }

        else throw new RuntimeException("No such event with provided ID.");
    }

    @Override
    @Transactional
    public Event editEvent(Long id, String name, String description, Double popularityScore, Location location) {
        Event event = new Event(id, name, description, popularityScore, location);
        eventRepository.deleteAllById(id);
        eventRepository.save(event);

        return event;
    }

    @Override
    @Transactional
    public Event addNewEvent(String name, String description, double popularityScore, Location location) {
        Event event = new Event(name, description, popularityScore);
        event.setLocation(location);
        eventRepository.save(event);

        return event;
    }

    @Override
    public List<Event> findEventAtLocation(Long id) {
        return eventRepository.findAllByLocation_Id(id);
    }

    @Override
    public List<Event> searchByLocation(String location) {
        return eventRepository.findAllByLocation_NameContainingIgnoreCase(location);
    }


}
