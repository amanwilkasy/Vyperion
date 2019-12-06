package com.vyperion.config.jwt;

import java.nio.charset.StandardCharsets;

final class JwtProperties {
    static final byte[] SECRET = System.getenv("JWT_SECRET").getBytes(StandardCharsets.UTF_8);
    static final int EXPIRATION = 3600000; // 1 hour
    static final String BEARER = "Bearer ";
    static final String AUTHORIZATION = "Authorization";
}
