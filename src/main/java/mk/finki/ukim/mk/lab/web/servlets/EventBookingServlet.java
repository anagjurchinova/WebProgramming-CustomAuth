package mk.finki.ukim.mk.lab.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.service.EventBookingService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "event-booking", urlPatterns = "/eventBooking-servlet")
public class EventBookingServlet extends HttpServlet  {
    private final EventBookingService eventBookingService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingServlet(EventBookingService eventBookingService, SpringTemplateEngine springTemplateEngine) {
        this.eventBookingService = eventBookingService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("eventName");
        String attendee = req.getParameter("attendeeName");
        int numTickets = 0;

        if (req.getParameter("numTickets") != null && !req.getParameter("numTickets").isEmpty()) {
            numTickets = Integer.parseInt(req.getParameter("numTickets"));
        }

        if (eventName != null && attendee != null && numTickets > 0) {
            String clientIP = req.getRemoteAddr();
                EventBooking booking = eventBookingService.placeBooking(eventName, attendee, clientIP, numTickets);
                req.getSession().setAttribute("eventBooking", booking);
                resp.sendRedirect("/eventBooking");
                return; // zatoa sto se naogja inside if statement, vo sprotivno server-side ke prodolzi da go execute ostanatiot kod
        }

        resp.sendRedirect("/");
    }
}
