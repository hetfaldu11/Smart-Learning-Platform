package com.fm.smartlearningplatform.dto.user.platform.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformResponse {

    private Long id;
    private String name;
}
