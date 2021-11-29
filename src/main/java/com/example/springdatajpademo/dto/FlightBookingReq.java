package com.example.springdatajpademo.dto;


import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingReq {

    private PassengerInfo passengerInfo;
    private BookingInfo bookingInfo;
    private PaymentInfo paymentInfo;

}
