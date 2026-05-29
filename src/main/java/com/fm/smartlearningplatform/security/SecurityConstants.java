package com.fm.smartlearningplatform.security;

public class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String SECRET_KEY =
            "thisIsMyVeryLongSecretKeyForJwtAuthentication123456789";

    public static final long
            ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    public static final long
            REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;
}