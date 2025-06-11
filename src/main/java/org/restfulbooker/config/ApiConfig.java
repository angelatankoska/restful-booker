package org.restfulbooker.config;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class ApiConfig {

    private static final ApiConfig INSTANCE = new ApiConfig();
    private final String baseUrl;
    private final String username;
    private final String password;

    private ApiConfig() {

        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration", ex);
        }

        this.baseUrl = properties.getProperty("baseUrl");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
    }

    public static ApiConfig getInstance() {
        return INSTANCE;
    }
}
