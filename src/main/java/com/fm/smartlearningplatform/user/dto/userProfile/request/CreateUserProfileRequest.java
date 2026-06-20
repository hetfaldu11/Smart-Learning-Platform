package com.fm.smartlearningplatform.user.dto.userProfile.request;

import com.fm.smartlearningplatform.user.dto.address.AddressDto;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record CreateUserProfileRequest(

        String firstName,
        String lastName,

        String aboutMe,

        Long educationLevelId,
        Long professionId,
        Long genderId,

        LocalDate dateOfBirth,

        AddressDto homeAddress,
        AddressDto workAddress,

        String instituteName,
        @URL(message = "Invalid URL format for profile picture url.")
        String profilePictureUrl

) {
    public CreateUserProfileRequest {
        if (firstName != null) {
            firstName = firstName.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (lastName != null) {
            lastName = lastName.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (aboutMe != null) {
            aboutMe = aboutMe.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (instituteName != null) {
            instituteName = instituteName.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (profilePictureUrl != null) {
            profilePictureUrl = profilePictureUrl.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}