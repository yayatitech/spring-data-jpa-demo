package com.example.springdatajpademo.controller;

import com.example.springdatajpademo.dto.Booking;
import com.example.springdatajpademo.dto.PassengerSearchCriteria;
import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.service.BookingService;
import com.example.springdatajpademo.service.FlightBookingService;
import com.example.springdatajpademo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/passengers")
public class PassengerController {

    private BookingService bookingService;
    private FlightBookingService flightBookingService;
    private PassengerService passengerService;

    @Autowired
    public PassengerController(BookingService bookingService, FlightBookingService flightBookingService, PassengerService passengerService) {
        this.bookingService = bookingService;
        this.flightBookingService = flightBookingService;
        this.passengerService = passengerService;
    }

    @GetMapping(value = "/{id}")
    public PassengerInfo getPassengersById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    @GetMapping(value = "/{id}/bookings")
    public List<Booking> getPassengerBookings(@PathVariable(required = true) Long id) {
        return bookingService.getBookingsByPassengerId(id);
    }

    @DeleteMapping(value = "/{id}")
    public String deletePassengersById(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return "success";
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<PassengerInfo> getPassengersByName(@RequestParam(required = false) Optional<String> name,
                                                   @RequestParam(required = false) Optional<String> city,
                                                   @RequestParam(required = false) Integer pageSize,
                                                   @RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false)  String sortBy) {
        PassengerSearchCriteria searchCriteria = new PassengerSearchCriteria();
        searchCriteria.setName(name);
        searchCriteria.setCity(city);

        return passengerService.search(searchCriteria, page, pageSize, sortBy);
    }


}
