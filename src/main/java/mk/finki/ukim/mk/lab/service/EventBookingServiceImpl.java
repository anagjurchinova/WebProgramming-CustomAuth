package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.EventBooking;
import mk.finki.ukim.mk.lab.repository.EventBookingRepository;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;

@Service
public class EventBookingServiceImpl implements EventBookingService{
    private final EventBookingRepository eventBookingRepository;

    public EventBookingServiceImpl(EventBookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
//        if(eventName == null || eventName.isEmpty() || attendeeName == null || attendeeName.isEmpty() || numberOfTickets <= 0){
//            throw new InvalidAttributeValueException("Please fill in all of the fields with required information.");
//        }
        return eventBookingRepository.addOrUpdate(eventName, attendeeName, attendeeAddress, numberOfTickets);
    }
}
