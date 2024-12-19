package mk.finki.ukim.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String capacity;
    private String description;

//    @OneToMany(mappedBy = "location")
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    public List<Event> events = new ArrayList<>();

    public Location(){}

    public Location(String name, String address, String capacity, String description) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }

        public Location(Long id, String name, String address, String capacity, String description) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.capacity = capacity;
            this.description = description;
        }
}
