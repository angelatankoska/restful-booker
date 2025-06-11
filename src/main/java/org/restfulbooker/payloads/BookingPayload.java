package org.restfulbooker.payloads;

import org.restfulbooker.models.Booking;
import org.restfulbooker.models.BookingDates;

public class BookingPayload {

    public Booking createBookingPayload(
            String firstName, String lastName, int totalPrice, boolean depositPaid, String checkIn, String checkOut, String additionalNeeds) {

        return Booking.builder()
                .firstname(firstName)
                .lastname(lastName)
                .totalprice(totalPrice)
                .depositpaid(depositPaid)
                .bookingdates(BookingDates.builder()
                        .checkin(checkIn)
                        .checkout(checkOut)
                        .build())
                .additionalneeds(additionalNeeds)
                .build();
    }
}