package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;
    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping
    public String getLocationsPage(Model model){
        model.addAttribute("locations", locationService.findAll());
        return "listLocations";
    }

    @GetMapping("/add")
    public String addLocation(){
        return "add-location";
    }

    @PostMapping("/save")
    public String saveLocation(@RequestParam(name = "location") String locationName,
                @RequestParam(name = "desc") String locationDesc,
                @RequestParam(name = "address") String locationAddress,
                @RequestParam(name = "capacity") String locationCapacity,
                @RequestParam(name = "locationId") String locationId) {

                locationService.addLocation(
                        locationName,
                        locationDesc,
                        locationAddress,
                        locationCapacity);

            return "redirect:/locations";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable(name = "id") String locationId){
        locationService.removeLocation(Long.parseLong(locationId));
        return "redirect:/locations";
    }


}
