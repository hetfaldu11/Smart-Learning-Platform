package com.fm.smartlearningplatform.user.dto.address;

public record AddressDto(

        String street,
        String city,
        String state,
        String country,
        String postalCode,
        String fullAddress

) {
}