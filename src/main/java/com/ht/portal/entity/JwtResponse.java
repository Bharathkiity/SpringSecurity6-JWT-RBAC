package com.ht.portal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String userName;
    private String accessToken;
    private String refreshToken;
    private String role;

    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    public static class JwtResponseBuilder {
        private String userName;
        private String accessToken;
        private String refreshToken;
        private String role;

        public JwtResponseBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public JwtResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public JwtResponseBuilder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public JwtResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(userName, accessToken, refreshToken, role);
        }
    }
}