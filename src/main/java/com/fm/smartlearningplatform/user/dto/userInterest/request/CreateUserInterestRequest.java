package com.fm.smartlearningplatform.user.dto.userInterest.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserInterestRequest(
        @NotNull(message = "interest Id required:")
        Long interestId
) {
}
