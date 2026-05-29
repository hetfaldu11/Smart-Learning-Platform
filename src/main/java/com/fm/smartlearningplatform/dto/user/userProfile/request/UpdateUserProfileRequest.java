package com.fm.smartlearningplatform.dto.user.userProfile.request;

import com.fm.smartlearningplatform.dto.user.address.AddressDto;

import java.time.LocalDate;

public record UpdateUserProfileRequest(

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

) {}