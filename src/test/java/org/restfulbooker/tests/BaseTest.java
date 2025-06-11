package org.restfulbooker.tests;

import com.github.javafaker.Faker;
import org.restfulbooker.clients.AuthClient;
import org.restfulbooker.clients.BookingClient;
import org.restfulbooker.config.ApiConfig;
import org.restfulbooker.models.Booking;
import org.restfulbooker.models.BookingDates;
import org.restfulbooker.payloads.BookingPayload;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeClass;

import java.time.LocalDate;

public class BaseTest {

    protected ApiConfig config = ApiConfig.getInstance();
    protected AuthClient authClient = new AuthClient();
    protected BookingClient bookingClient = new BookingClient();
    protected Faker faker = new Faker();
    protected SoftAssertions softly = new SoftAssertions();
    protected BookingPayload bookingPayload = new BookingPayload();

    @BeforeClass
    public void setup() {

        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.METHOD),
                new RequestLoggingFilter(LogDetail.URI),
                new RequestLoggingFilter(LogDetail.BODY),
                new ResponseLoggingFilter(LogDetail.BODY),
                new ResponseLoggingFilter(LogDetail.STATUS)
        );

        RestAssured.baseURI = config.getBaseUrl();
    }

    protected Booking createRandomBooking() {

        BookingDates dates = BookingDates.builder()
                .checkin(LocalDate.now().plusDays(5).toString())
                .checkout(LocalDate.now().plusDays(10).toString())
                .build();

        return Booking.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(100, 400))
                .depositpaid(true)
                .bookingdates(dates)
                .additionalneeds(faker.options().option("Breakfast", "Lunch", "Dinner"))
                .build();
    }

    protected Booking createBookingPayload() {

        return bookingPayload.createBookingPayload(
                "Susan",
                "Mayer",
                250,
                true,
                LocalDate.now().plusDays(5).toString(),
                LocalDate.now().plusDays(10).toString(),
                "Lunch");
    }
}
