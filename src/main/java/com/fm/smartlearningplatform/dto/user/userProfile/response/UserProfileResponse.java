package com.fm.smartlearningplatform.dto.user.userProfile.response;

import com.fm.smartlearningplatform.dto.user.address.AddressDto;

import java.time.LocalDate;

public record UserProfileResponse(

        Long userId,

        String firstName,
        String lastName,
        String aboutMe,

        Long educationLevelId,
        String educationLevelName,

        Long professionId,
        String professionName,

        Long genderId,
        String genderName,

        LocalDate dateOfBirth,

        AddressDto homeAddress,
        AddressDto workAddress,

        String instituteName,
        String profilePictureUrl

) {}