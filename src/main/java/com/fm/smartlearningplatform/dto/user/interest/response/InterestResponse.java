package com.fm.smartlearningplatform.dto.user.interest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestResponse {

    private Long id;
    private String name;
}
