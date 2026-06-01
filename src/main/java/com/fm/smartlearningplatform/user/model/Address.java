package com.fm.smartlearningplatform.user.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String fullAddress;
}
