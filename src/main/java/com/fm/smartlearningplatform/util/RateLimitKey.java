package com.fm.smartlearningplatform.util;

public class RateLimitKey {

    public static String loginKey(String deviceId, String ip){
        return deviceId + ":" + ip;
    }

    public static String rateLimitKey(String type, String key){
        return "rate_limit:" + type + ":" + key;
    }

}