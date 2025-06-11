package org.restfulbooker.clients;

import org.restfulbooker.config.ApiConfig;
import org.restfulbooker.models.AuthRequest;
import org.restfulbooker.models.AuthResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.java.Log;

import static io.restassured.RestAssured.given;

@Log
public class AuthClient {

    private final ApiConfig config = ApiConfig.getInstance();

    public String getAuthToken() {

        log.info("Sending the `POST /auth` request and retrieving the response");

        AuthRequest authRequest = AuthRequest.builder()
                .username(config.getUsername())
                .password(config.getPassword())
                .build();

//        NOTE: this snippet has the same function as the one below
//        Response response = RestAssured
//                .given()
//                .contentType(ContentType.JSON)
//                .body(authRequest)
//                .post(config.getBaseUrl() + "/auth")
//                .then()
//                .contentType(ContentType.JSON)
//                .extract()
//                .response();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .post(config.getBaseUrl() + "/auth")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();

        return response.as(AuthResponse.class).getToken();
    }
}
