package org.restfulbooker.models;

public class AuthRequest {
    private String username;
    private String password;

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Builder
    public static class Builder {
        private String username;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public AuthRequest build() {
            AuthRequest request = new AuthRequest();
            request.username = this.username;
            request.password = this.password;
            return request;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

