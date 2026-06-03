package com.fm.smartlearningplatform.security.usersession;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {

    private String browserName;
    private String browserVersion;

    private String osName;
    private String osVersion;

    private String deviceName;
    private String deviceType;
}