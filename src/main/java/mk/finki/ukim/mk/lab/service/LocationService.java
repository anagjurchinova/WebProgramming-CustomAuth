package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Location;

import java.util.List;

public interface LocationService {
    public List<Location> findAll();
    public Location findLocation(Long id);

    public void removeLocation(Long id);
    public void addLocation(String name, String address, String capacity, String description);
}
