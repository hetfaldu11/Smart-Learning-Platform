package com.fm.smartlearningplatform.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
public class Address {
    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;

    private String fullAddress;
}
