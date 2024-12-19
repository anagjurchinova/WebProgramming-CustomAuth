package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {
    private final EventBookingService eventBookingService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingController(EventBookingService eventBookingService, SpringTemplateEngine springTemplateEngine) {
        this.eventBookingService = eventBookingService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @GetMapping
    public String getBookingConfirmation() {
        return "bookingConfirmation";
    }

    @PostMapping
    public String makeBooking(HttpServletRequest req,
                              @RequestParam(name = "eventName") String eventName,
                              @RequestParam(name = "attendeeName") String attendeeName,
                              @RequestParam(name = "numTickets", required = false) String tickets) {
        int numTickets = 0;
        if (tickets != null) {
            numTickets = Integer.parseInt(tickets);
        }
        if (eventName != null && attendeeName != null && numTickets > 0) {
            String clientIP = req.getRemoteAddr();
            EventBooking booking = eventBookingService.placeBooking(eventName, attendeeName, clientIP, numTickets);
            req.getSession().setAttribute("eventBooking", booking);

            return "redirect:/eventBooking";
        }
        return "redirect:/events";
    }
}
