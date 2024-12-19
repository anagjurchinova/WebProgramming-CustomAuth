package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventRepository {

    private static List<Event> events = new ArrayList(10);

    public InMemoryEventRepository(){
        events.add(new Event("Ohrid Calling", "Summer 2025, EDM music festival", 76.3));
        events.add(new Event("D-Festival", "Summer 2025, EDM music festival", 83));
    }

    public List<Event> findAll(){
        return events;
    }
    public List<Event> searchEvent(String text){
        return events
                .stream()
                .filter(e -> e.getName().contains(text) || e.getDescription().contains(text))
                .collect(Collectors.toList());
    }

    public List<Event> searchByRating(double popularityScore){
        return events
                .stream()
                .filter(e -> e.getPopularityScore() >= popularityScore)
                .collect(Collectors.toList());
    }

    public Optional<Event> searchById(Long id){
        return events
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Event> searchByLocation(Long id){
        return events
                .stream()
                .filter(e -> e.getLocation().getId().equals(id))
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        events.removeIf(e -> e.getId().equals(id));
    }

    public void saveOrUpdate(Event event){
        events.removeIf(e -> e.getId().equals(event.getId()));
        events.add(event);
    }
}
