package com.book.config.security.jwt;

import lombok.Getter;

@Getter
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
