package mk.finki.ukim.mk.lab.web.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.model.Location;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

// TODO: 14.11.2024 FIX SESSION COUNTER

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EventBookingService eventBookingService;
    private final SpringTemplateEngine springTemplateEngine;
    private final LocationService locationService;

    public EventController(EventService eventService,
                           SpringTemplateEngine springTemplateEngine,
                           EventBookingService eventBookingService,
                           LocationService locationService) {

        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
        this.eventBookingService = eventBookingService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model, HttpServletRequest req){
        String searchedByName = (String) req.getSession().getAttribute("searchedByName");
        String searchedByRating = (String) req.getSession().getAttribute("searchedByRating");
        String searchedByLocation = (String) req.getSession().getAttribute("searchedByLocation");

        List<Event> filteredEvents = new ArrayList<>();

        if((searchedByName == null || searchedByName.isEmpty()) && (searchedByRating == null || searchedByRating.isEmpty())
        && (searchedByLocation == null || searchedByLocation.isEmpty())){

            filteredEvents = eventService.listAll();
        }
        if(searchedByName != null && !searchedByName.isEmpty()) {
            filteredEvents = eventService.searchEvents(searchedByName);
        }
        if(searchedByRating != null && !searchedByRating.isEmpty()){
            filteredEvents = eventService.searchByRating(searchedByRating);
        }
        if(searchedByLocation != null && !searchedByLocation.isEmpty()){
            filteredEvents = eventService.searchByLocation(searchedByLocation);
        }

        // TODO: 14.11.2024 FIX SESSION COUNTER
        Integer sessionCounter = (Integer) model.getAttribute("sessionCounter");
        HttpSession session = req.getSession();

        if(session.isNew()) {
            model.addAttribute("sessionCounter", 0);
        }
        model.addAttribute("sessionCounter", model.getAttribute("sessionCounter"));
        model.addAttribute("eventList", filteredEvents);

        return "listEvents";
    }

    @PostMapping
    public String searchEvents(@RequestParam(required = false) String searchedByName,
                               @RequestParam(required = false) String searchedByLocation,
                               @RequestParam(required = false) String searchedByRating,
                               HttpServletRequest req, Model model) {

        if (searchedByName != null && !searchedByName.isEmpty()) {
            req.getSession().setAttribute("searchedByName", searchedByName);
        }
        if (searchedByLocation != null && !searchedByLocation.isEmpty()) {
            req.getSession().setAttribute("searchedByLocation", searchedByLocation);
        }
        if (searchedByRating != null && !searchedByRating.isEmpty()) {
            try{
                Double.parseDouble(searchedByRating);
                req.getSession().setAttribute("searchedByRating", searchedByRating);

            }catch (IllegalArgumentException ex){
                req.getSession().setAttribute("hasError", true);
                req.getSession().setAttribute("error", "Please enter a valid rating format.");
            }
        }

        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable(name = "id") String eventId, Model model){
        Event event = eventService.searchById(Long.parseLong(eventId));
        model.addAttribute("event", event);
        model.addAttribute("eventId", Long.parseLong(eventId));
        System.out.println("event id: " + eventId);
        model.addAttribute("locations", locationService.findAll());
        return "add-event";
    }

    @GetMapping("/add")
    public String addEvent(Model model){
        model.addAttribute("event", null);
        model.addAttribute("eventId", null);
        model.addAttribute("locations", locationService.findAll());
        return "add-event";
    }


    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable(name = "id") String eventId){
        System.out.println(eventId);
        this.eventService.delete(Long.parseLong(eventId));
        return "redirect:/events";
    }

    @PostMapping("/save-event")
    public String saveEvent(@RequestParam(name = "event") String eventName,
                            @RequestParam(name = "desc") String eventDesc,
                            @RequestParam(name = "rating") String eventRating,
                            @RequestParam(name = "locationId") String locationId,
                            @RequestParam(name = "eventId") String eventId) {

        Location location = locationService.findLocation(Long.parseLong(locationId));
        if(eventId == null || eventId.equals("Empty") || eventId.isEmpty()){
            eventService.addNewEvent(eventName, eventDesc, Double.parseDouble(eventRating), location);
        } else {
            eventService.editEvent(Long.parseLong(eventId),
                    eventName,
                    eventDesc,
                    Double.parseDouble(eventRating),
                    location);
        }
        return "redirect:/events";
    }
}