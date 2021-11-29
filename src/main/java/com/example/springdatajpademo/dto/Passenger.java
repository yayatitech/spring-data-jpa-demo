package com.example.springdatajpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String address;
    private String city;
    private String country;
}
