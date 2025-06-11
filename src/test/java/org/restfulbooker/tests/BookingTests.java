package org.restfulbooker.tests;

import org.restfulbooker.models.Booking;
import org.restfulbooker.models.BookingDates;
import org.restfulbooker.models.BookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingTests extends BaseTest {

    @Test
    public void testCreateAndGetBooking() {
        Booking newBooking = createRandomBooking();

        Response createResponse = bookingClient.createBooking(newBooking);
        assertThat(createResponse.getStatusCode()).isEqualTo(200);

        BookingResponse bookingResponse = createResponse.as(BookingResponse.class);
        int bookingId = bookingResponse.getBookingid();

        Response getResponse = bookingClient.getBookingById(bookingId);
        assertThat(getResponse.getStatusCode()).isEqualTo(200);
        softly.assertThat(getResponse.getContentType()).startsWith(ContentType.JSON.toString());

        Booking retrievedBooking = getResponse.as(Booking.class);
        softly.assertThat(retrievedBooking.getFirstname()).isEqualTo(newBooking.getFirstname());
        softly.assertThat(retrievedBooking.getLastname()).isEqualTo(newBooking.getLastname());
        softly.assertAll();
    }

    @Test
    public void testGetAllBookingIds() {
        Response response = bookingClient.getAllBookingIds();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("bookingid")).isNotEmpty();
    }

    @Test
    public void testUpdateBooking() {
        Booking original = createRandomBooking();
        int bookingId = bookingClient.createBooking(original)
                .as(BookingResponse.class)
                .getBookingid();

        Booking updated = Booking.builder()
                .firstname("Updated")
                .lastname("User")
                .totalprice(222)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-06-01", "2025-06-10"))
                .additionalneeds("Dinner")
                .build();

        String token = authClient.getAuthToken();

        Response updateResponse = bookingClient.updateBooking(bookingId, updated, token);
        assertThat(updateResponse.statusCode()).isEqualTo(200);

        Booking afterUpdate = updateResponse.as(Booking.class);
        assertThat(afterUpdate.getFirstname()).isEqualTo("Updated");
        assertThat(afterUpdate.getLastname()).isEqualTo("User");
    }

    @Test
    public void testPartialUpdateBooking() {
        Booking booking = createRandomBooking();
        int bookingId = bookingClient.createBooking(booking)
                .as(BookingResponse.class)
                .getBookingid();

        Booking partial = Booking.builder()
                .firstname("Partial")
                .build();

        String token = authClient.getAuthToken();

        Response response = bookingClient.partialUpdateBooking(bookingId, partial, token);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("firstname")).isEqualTo("Partial");
    }

    @Test
    public void testDeleteBooking() {
        Booking booking = createRandomBooking();
        int bookingId = bookingClient.createBooking(booking)
                .as(BookingResponse.class)
                .getBookingid();

        Response deleteResponse = bookingClient.deleteBooking(bookingId);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(201);

        try {
            bookingClient.getBookingById(bookingId);
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage()).contains("not found");
        }
    }

    @Test
    public void testCreateBookingWithInvalidData() {
        Booking invalidBooking = Booking.builder()
                .firstname("")
                .lastname(null)
                .totalprice(-1)
                .depositpaid(false)
                .bookingdates(new BookingDates("invalid-date", "invalid-date"))
                .additionalneeds("None")
                .build();

        Response response = bookingClient.createBooking(invalidBooking);
        assertThat(response.getStatusCode()).isIn(400, 500);
    }

    @Test
    public void testGetNonExistentBooking() {
        int nonExistentId = 999999;
        Response response = bookingClient.getBookingById(nonExistentId);
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void testBookingDateRangeValidation() {
        Booking invalidDateRangeBooking = Booking.builder()
                .firstname("Test")
                .lastname("DateCheck")
                .totalprice(100)
                .depositpaid(true)
                .bookingdates(new BookingDates("2025-07-10", "2025-07-01"))
                .additionalneeds("Nothing")
                .build();

        Response response = bookingClient.createBooking(invalidDateRangeBooking);
        assertThat(response.getStatusCode()).isIn(400, 422);
    }



}
