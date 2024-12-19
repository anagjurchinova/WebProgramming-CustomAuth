package mk.finki.ukim.mk.lab.repository;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryLocationRepository {
    private static List<Location> locations = new ArrayList<>();
    private final InMemoryEventRepository eventRepository;

    public InMemoryLocationRepository(InMemoryEventRepository eventRepository){
        this.eventRepository = eventRepository;
        locations.add(new Location("Ohrid kasarna", "Stara Kasarna, Radoica Novichich b.b", "20000", "Festivals are held every summer near the old military base."));
        locations.add(new Location("Dojran", "Star Dojran", "100000", "Different stages for each performer."));
        locations.add(new Location("MKC Skopje", "street Dimitar Vlahov, 1000", "15000", "Alternative concerts and events held each year."));
        locations.add(new Location("Veles", "Gimnazija Kocho Racin, Veles", "10000", "Gimnazija Kocho Racin is a highschool in Veles. Many events are held on a stage in the middle of the street."));
        locations.add(new Location("Hotel Romantique Veles", "Mladost Lake, Veles 1400", "600", "Your best place for weddings, parties and fine dining."));

    }

    @PostConstruct
    public void init(){
        final int[] counter = {0};
        eventRepository.findAll()
                .stream()
                .forEach(e -> {
                    e.setLocation(locations.get(counter[0]));
                    counter[0]++;
                });
    }

    public List<Location> findAll(){
        return locations;
    }

    public Optional<Location> searchById(Long id){
        return locations
                .stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    public void remove(Location location){
        locations.removeIf(l -> l.getId().equals(location.getId()));
    }

    public void addLocation(Location location){
        locations.removeIf(l -> l.getId().equals(location.getId()));
        locations.add(location);
    }

}
