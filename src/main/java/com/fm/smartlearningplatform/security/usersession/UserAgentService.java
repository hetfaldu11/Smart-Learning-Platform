package com.fm.smartlearningplatform.security.usersession;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Component;

@Component
public class UserAgentService {

    private final UserAgentAnalyzer analyzer = UserAgentAnalyzer.newBuilder()
                    .hideMatcherLoadStats()
                    .withCache(1000)
                    .build();

    public DeviceInfo parse(String userAgentString) {

        UserAgent agent = analyzer.parse(userAgentString);
        return DeviceInfo.builder()
                .browserName(agent.getValue(UserAgent.AGENT_NAME))
                .browserVersion(agent.getValue(UserAgent.AGENT_VERSION))
                .osName(agent.getValue(UserAgent.OPERATING_SYSTEM_NAME))
                .osVersion(agent.getValue(UserAgent.OPERATING_SYSTEM_VERSION))
                .deviceName(agent.getValue(UserAgent.DEVICE_NAME))
                .deviceType(agent.getValue(UserAgent.DEVICE_CLASS))
                .build();
    }
}