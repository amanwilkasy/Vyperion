package com.vyperion.config.jwt;

import java.nio.charset.StandardCharsets;

final class JwtProperties {
    static final byte[] SECRET = System.getenv("JWT_SECRET").getBytes(StandardCharsets.UTF_8);
    static final int EXPIRATION = 86400000; // 1 day
    static final String BEARER = "Bearer ";
    static final String AUTHORIZATION = "Authorization";
}
