package com.fm.smartlearningplatform.user.dto.userProfile.response;

import com.fm.smartlearningplatform.user.dto.address.AddressDto;

import java.time.LocalDate;

public record UserProfileResponse(

        Long userId,

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
        String profilePictureUrl

) {
}