package com.fm.smartlearningplatform.dto.user.address;

public record AddressDto(

        String street,
        String city,
        String state,
        String country,
        String postalCode,
        String fullAddress

) {}