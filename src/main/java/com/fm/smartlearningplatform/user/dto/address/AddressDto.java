package com.fm.smartlearningplatform.user.dto.address;

public record AddressDto(

        String street,
        String city,
        String state,
        String country,
        String postalCode,
        String fullAddress

) {
    public AddressDto {
        if (street != null) {
            street = street.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (city != null) {
            city = city.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (state != null) {
            state = state.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (country != null) {
            country = country.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (postalCode != null) {
            postalCode = postalCode.trim().replaceAll("\\s+", " ").toLowerCase();
        }
        if (fullAddress != null) {
            fullAddress = fullAddress.trim().replaceAll("\\s+", " ").toLowerCase();
        }
    }
}