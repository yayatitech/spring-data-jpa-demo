package com.example.springdatajpademo.controller;

import com.example.springdatajpademo.dto.FlightBookingAck;
import com.example.springdatajpademo.dto.FlightBookingReq;
import com.example.springdatajpademo.dto.PassengerSearchCriteria;
import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.service.BookingService;
import com.example.springdatajpademo.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @RestControoler just eliminates @ResponseBody, @RequestBody is still required.
 */

@Controller
@RequestMapping(value = "/booking")
public class BookFlightController {

    @Autowired
    private FlightBookingService flightBookingService;

    private BookingService bookingService;

    @PostMapping(value = "/bookFlightTicket")
    public ResponseEntity<FlightBookingAck> bookFlightTicket(@RequestBody @Valid FlightBookingReq flightBookingReq) {
        FlightBookingAck flightBookingAck = null;
        try {
            flightBookingAck = flightBookingService.bookFlightTicket(flightBookingReq);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(flightBookingAck, HttpStatus.CREATED);
    }

    private DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd-MMM-yyyy");

//    @GetMapping(value = "/passenger-search")
//    public List<PassengerInfo> getPassengersByName(@RequestParam(required = false) Optional<String> travelDate) {
//        /*LocalDate localDate = null;
//        if(travelDate.isPresent()) {
//            try {
//                localDate = LocalDate.parse(travelDate.get(), dateTimeFormatter);
//            } catch (Exception ex) {
//                throw new RuntimeException("Invalid search criteria value for travelDate " + travelDate);
//            }
//            return flightBookingService.getAllPassengers(localDate);
//        }*/
//        return flightBookingService.getAllPassengers();
//    }

}
