package com.fm.smartlearningplatform.user.dto.userInterest.request;

import java.util.List;

public record CreateUserInterestsRequest(
        List<Long> interestIds
) {
}