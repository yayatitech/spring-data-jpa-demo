package com.example.springdatajpademo.service;

import com.example.springdatajpademo.dto.FlightBookingAck;
import com.example.springdatajpademo.dto.FlightBookingReq;
import com.example.springdatajpademo.dto.PassengerSearchCriteria;
import com.example.springdatajpademo.repository.PassengerSpecification;
import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.entity.PaymentInfo;
import com.example.springdatajpademo.repository.BookingRepository;
import com.example.springdatajpademo.repository.PassengerInfoRepository;
import com.example.springdatajpademo.repository.PaymentInfoRepository;
import com.example.springdatajpademo.utils.PaymentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class FlightBookingService {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public PassengerInfo getPassengerByName(String name) {
        List<PassengerInfo> passengerInfos = passengerInfoRepository.findByName(name);
        return passengerInfos.isEmpty() ? null : passengerInfos.get(0);
    }


    public FlightBookingAck bookFlightTicket(FlightBookingReq flightBookingReq) {
        PassengerInfo passengerInfo = getPassengerByName(flightBookingReq.getPassengerInfo().getName());
        if (passengerInfo == null) {
            passengerInfo = passengerInfoRepository.save(flightBookingReq.getPassengerInfo());
        }

        BookingInfo bookingInfo = flightBookingReq.getBookingInfo();
        bookingInfo.setPassengerInfo(passengerInfo);
        bookingRepository.save(bookingInfo);

        PaymentInfo paymentInfo = flightBookingReq.getPaymentInfo();
        PaymentUtils.validateCredit(paymentInfo.getAccountNo(), paymentInfo.getAmount());
        paymentInfo.setPassengerInfo(passengerInfo);
        paymentInfoRepository.save(paymentInfo);

        return new FlightBookingAck("SUCCESS", flightBookingReq.getBookingInfo().getFare(), UUID.randomUUID().toString().split("-")[0], passengerInfo);
    }

    @Transactional(readOnly = true)
    public List<BookingInfo> getAllBookings(LocalDate travelDate) {
        return bookingRepository.findByTravelDate(travelDate);
    }

    @Transactional(readOnly = true)
    public List<PassengerInfo> getAllPassengers(Map<String, String> reqParameters) {
        return passengerInfoRepository.findAll();
    }






}
