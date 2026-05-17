package com.fm.smartlearningplatform.model.user;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Address {
    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String fullAddress;
}
