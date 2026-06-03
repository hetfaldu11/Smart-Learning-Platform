package com.fm.smartlearningplatform.security.usersession;

public record GeoInfo(
        String country,
        String city,
        String ip
) {
}
