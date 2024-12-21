package mk.finki.ukim.mk.lab.data;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import mk.finki.ukim.mk.lab.enumeration.Role;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.EventRepository;
import mk.finki.ukim.mk.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(LocationRepository locationRepository, EventRepository eventRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeLocations() {
        Location ohridKasarna = new Location("Ohrid kasarna", "Stara Kasarna, Radoica Novichich b.b", "20000", "Festivals are held every summer near the old military base.");
        Location dojran = new Location("Dojran", "Star Dojran", "100000", "Different stages for each performer.");
        Location mkcSkopje = new Location("MKC Skopje", "street Dimitar Vlahov, 1000", "15000", "Alternative concerts and events held each year.");
        Location mkcVeles = new Location("Veles", "Gimnazija Kocho Racin, Veles", "10000", "Gimnazija Kocho Racin is a highschool in Veles. Many events are held on a stage in the middle of the street.");
        Location romantikVeles = new Location("Hotel Romantique Veles", "Mladost Lake, Veles 1400", "600", "Your best place for weddings, parties and fine dining.");

        locationRepository.saveAll(List.of(ohridKasarna, dojran, mkcSkopje, mkcVeles, romantikVeles));

        Event ohridCalling = new Event("Ohrid Calling", "Summer 2025, EDM music festival", 76.3);
        Event dFest = new Event("D-Festival", "Summer 2025, EDM music festival", 83);

        ohridCalling.setLocation(ohridKasarna);
        dFest.setLocation(dojran);

        eventRepository.saveAll(List.of(ohridCalling, dFest));
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), Role.ROLE_ADMIN));
    }
}
