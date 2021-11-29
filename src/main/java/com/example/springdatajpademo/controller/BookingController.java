package com.example.springdatajpademo.controller;

import com.example.springdatajpademo.dto.Booking;
import com.example.springdatajpademo.dto.BookingSearchCriteria;
import com.example.springdatajpademo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "bookings")
@Validated //class level
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @GetMapping(value = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Booking getPassengerBookingById(@PathVariable(required = true) Long bookingId) {
        return bookingService.findByBookingId(bookingId);
    }

    @DeleteMapping(value = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBookings(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return "success";
    }

    //bookings/1231
    @PutMapping(value = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public Booking updateBooking(@PathVariable @Min(value = 0) @Max(value=5)Long bookingId,
                                 @RequestBody @Valid Booking booking) {
        return bookingService.updateBooking(bookingId, booking);
    }

    ///bookings?tradeDateFrom=12-12-2014&travelDateTo=31-12-2015
    @GetMapping
    public List<Booking> search(@RequestParam(required = false) Optional<String> travelDateFrom,
                                                   @RequestParam(required = false) Optional<String> travelDateTo,
                                                   @RequestParam(required = false) Optional<String> source,
                                                   @RequestParam(required = false) Optional<String> destination,
                                                   @RequestParam(required = false) Optional<Integer> page,
                                                   @RequestParam(required = false) Optional<Integer> pageSize,
                                                   @RequestParam(required = false)  Optional<String> sortBy) {
        BookingSearchCriteria searchCriteria = new BookingSearchCriteria();
        searchCriteria.setSource(source);
        searchCriteria.setDestination(destination);
        searchCriteria.setTravelDateFrom(travelDateFrom);
        searchCriteria.setTravelDateTo(travelDateTo);

        Pageable pageable = null;
        if(page.isPresent() && pageSize.isPresent()) {
            if(sortBy.isPresent()) {
                pageable = PageRequest.of(page.get(), pageSize.get(), Sort.by(sortBy.get()));
            } else {
                pageable = PageRequest.of(page.get(), pageSize.get());
            }
        }

        List<Booking> result = bookingService.getAllBookingsUsingCriteria(searchCriteria, pageable);
        System.out.println("total elements = "+ result != null ? result.size() : 0);
        return result;
    }
}
