package com.fm.smartlearningplatform.mapper.user;

import com.fm.smartlearningplatform.dto.user.userProfile.request.CreateUserProfileRequest;
import com.fm.smartlearningplatform.dto.user.userProfile.request.UpdateUserProfileRequest;
import com.fm.smartlearningplatform.dto.user.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.model.user.*;


public interface UserProfileMapper {
    UserProfile toEntity(
            CreateUserProfileRequest request,
            User user,
            EducationLevel educationLevel,
            Profession profession,
            Gender gender
    );

    UserProfileResponse toResponse(UserProfile profile);


    void updateUserProfileFromRequest(
            UpdateUserProfileRequest request,
            UserProfile profile
    );
}
