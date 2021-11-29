package com.example.springdatajpademo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerSearchCriteria {

    private Optional<String> name;
    private Optional<String>  email;
    private Optional<String> city;


}
