package org.restfulbooker.clients;

import org.restfulbooker.config.ApiConfig;
import org.restfulbooker.models.Booking;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.java.Log;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;


@Log
public class BookingClient {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private final ApiConfig config = ApiConfig.getInstance();
    private String token;
    private final AuthClient authClient = new AuthClient();

    private final RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri(config.getBaseUrl())
            .build();

    private RequestSpecification authRequestSpec(String token) {
        return new RequestSpecBuilder()
                .addHeader("Cookie", "token=" + token)
                .setBaseUri(config.getBaseUrl())
                .build();
    }

    private final ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .build();

    // TODO: public Response getAllBookingIds() { ... }
    public Response getAllBookingIds() {
        return given()
                .baseUri(BASE_URL)
                .basePath("/booking")
                .when()
                .get();
    }


    // TODO: public Response getBookingById(int id) { ... }
    public Response getBookingById(int id) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath("/booking/" + id)
                .when()
                .get();

        if (response.statusCode() == 200) {
            return response;
        } else {
            throw new RuntimeException("Booking with ID " + id + " not found. Status code: " + response.statusCode());
        }
    }


    public Response createBooking(Booking booking) {

        log.info("Sending the `POST /booking` request and retrieving the response");

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .spec(baseRequestSpec)
                .body(booking)
                .post("/booking")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    // TODO: public Response updateBooking(int id, Booking booking) { ... }
    public Response updateBooking(int id, Booking booking, String token) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/booking/" + id)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(booking)
                .when()
                .put();
    }

    // TODO: public Response partialUpdateBooking(int id, Booking booking) { ... }
    public Response partialUpdateBooking(int id, Booking booking, String token) {
        return given()
                .baseUri(BASE_URL)
                .basePath("/booking/" + id)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(booking)
                .when()
                .patch();
    }

    public Response deleteBooking(int id) {

        token = authClient.getAuthToken();

        log.info("Sending the `DELETE /booking/{id}` request and retrieving the response");

        return RestAssured
                .given()
                .spec(authRequestSpec(token))
                .delete("/booking/" + id)
                .then()
                .contentType(ContentType.TEXT)
                .extract()
                .response();
    }
}
