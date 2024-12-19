package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Event {
    String name;

    String description;

    double popularityScore;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Location location;
    public Event(){}

    public Event(String name, String description, double popularityScore) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
    }

    public Event(Long id, String name, String description, Double popularityScore, Location location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
    }

}
