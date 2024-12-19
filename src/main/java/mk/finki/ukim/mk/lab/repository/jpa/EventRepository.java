package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByLocation_Id(long locationId);

    // TODO: 05.12.2024 implement by id instead of by name 
    Optional<Event> findAllById(long id);
    List<Event> findAllByPopularityScoreGreaterThanEqual(double popularityScore);

    List<Event> findAllByNameContainingIgnoreCase(String name);
    List<Event> findAllByLocation_NameContainingIgnoreCase(String locationName);
    void deleteAllById(long id);
}
