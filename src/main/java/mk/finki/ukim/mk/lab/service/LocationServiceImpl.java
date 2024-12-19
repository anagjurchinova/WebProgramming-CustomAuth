package mk.finki.ukim.mk.lab.service;

import jakarta.transaction.Transactional;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.repository.InMemoryEventRepository;
import mk.finki.ukim.mk.lab.repository.InMemoryLocationRepository;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    public LocationServiceImpl(LocationRepository locationRepository, EventRepository eventRepository) {
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findLocation(Long id) {
        // TODO: 14.11.2024 CHECK IF EXISTS
        return locationRepository.findAllById(id).get();
    }

    @Override
    @Transactional
    public void removeLocation(Long id) {
        List<Event> eventsAtLocation = eventRepository.findAllByLocation_Id(id);
        if(eventsAtLocation.size() > 0 && eventsAtLocation != null && !eventsAtLocation.isEmpty()){
            eventsAtLocation
                    .stream()
                    .forEach(e -> eventRepository.deleteAllById(e.getId()));
        }
        // TODO: 15.11.2024 check if present logic
        Optional<Location> location = locationRepository.findAllById(id);
        if(!location.isEmpty()) {
            locationRepository.deleteAllById(id);
        }
    }

    @Override
    @Transactional
    public void addLocation(String name, String address, String capacity, String description) {
        Location location = new Location(name, address, capacity, description);
        locationRepository.deleteAllByName(name);
        locationRepository.save(location);
    }


}
