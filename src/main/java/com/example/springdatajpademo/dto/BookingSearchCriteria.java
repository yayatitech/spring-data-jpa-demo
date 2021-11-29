package com.example.springdatajpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingSearchCriteria {

    private Optional<String> source;
    private Optional<String>  destination;
    private Optional<String>  travelDateFrom;
    private Optional<String>  travelDateTo;
    private Optional<Double> fareFrom;
    private Optional<Double> fareTo;
}
