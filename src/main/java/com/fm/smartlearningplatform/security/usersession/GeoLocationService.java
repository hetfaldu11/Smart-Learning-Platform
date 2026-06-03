package com.fm.smartlearningplatform.security.usersession;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final DatabaseReader databaseReader;

    public GeoInfo getGeoInfo(String ip)
            throws IOException, GeoIp2Exception {

        InetAddress inetAddress = InetAddress.getByName(ip);

        CityResponse response = databaseReader.city(inetAddress);

        return new GeoInfo(response.country().name(), response.city().name(), ip);
    }
}