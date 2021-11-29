package com.example.springdatajpademo.dto;

import com.example.springdatajpademo.entity.OnCreate;
import com.example.springdatajpademo.entity.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @NotNull(groups = OnUpdate.class) //validation groups
    @Null(groups = OnCreate.class)
    public Long id;

    @NotBlank(message = "source is mandatory") //@NotBlank or @NotEmpty annotation must be applied on any String field only
    private String source;

    @NotBlank(message = "destination is mandatory")
    private String destination;

    //@NotBlank(message = "travelDate is mandatory")  java 8 date type validation is not supported
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate travelDate;

    @NotNull(message = "fare is mandatory")
    private Double fare;
}
