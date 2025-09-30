package com.example.jpa_project.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Value Object
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
