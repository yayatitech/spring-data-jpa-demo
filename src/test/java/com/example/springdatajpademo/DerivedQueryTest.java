package com.example.springdatajpademo;


import com.example.springdatajpademo.dto.FlightBookingAck;
import com.example.springdatajpademo.dto.FlightBookingReq;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.entity.PaymentInfo;
import com.example.springdatajpademo.repository.PassengerInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DerivedQueryTest {

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;
    private PassengerInfo pass2;

    @Before
    public void setUp() {
        passengerInfoRepository.deleteAll();

        PassengerInfo pass1 = createPassengerInfo("Yayati", "yayati@gmail.com", 4000);
        passengerInfoRepository.save(pass1);
        passengerInfoRepository.save(createPassengerInfo("Rashmi", "rashmi@gmail.com", 5000));
        passengerInfoRepository.save(createPassengerInfo("Advay", "advay@gmail.com", 6000));
        passengerInfoRepository.save(createPassengerInfo("Anay", "anay@gmail.com", 8000));

        FlightBookingReq flightBookingReq = new FlightBookingReq();
        flightBookingReq.setPassengerInfo(pass1);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(3232.4);
        paymentInfo.setAccountNo("acc1");
        paymentInfo.setCardType("CREDIT");

        flightBookingReq.setPaymentInfo(paymentInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(flightBookingReq));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private PassengerInfo createPassengerInfo(String name, String email, double fare) {
        PassengerInfo pass1 = new PassengerInfo();
        pass1.setName(name);
        pass1.setEmail(email);
        return pass1;
    }

    @Test
    public void testFindAll() {
        List<PassengerInfo> passengerInfoList = passengerInfoRepository.findAll();
        Assert.assertEquals(4, passengerInfoList.size());
    }

    @Test
    public void findPassengerWithLastName() {
        List<PassengerInfo> passengerInfoList = passengerInfoRepository.findByName("Rashmi");
        Assert.assertEquals(1, passengerInfoList.size());
        Assert.assertEquals("Rashmi", passengerInfoList.get(0).getName());
    }

    @Test
    public void testSorting() {
        List<PassengerInfo> passengerInfoList = passengerInfoRepository.findAll(Sort.by("name", "travelDate"));
        Assert.assertEquals("Advay", passengerInfoList.get(0).getName());

        passengerInfoList = passengerInfoRepository.findAll(Sort.by("name", "travelDate").descending());
        Assert.assertEquals("Yayati", passengerInfoList.get(0).getName());

        passengerInfoList = passengerInfoRepository.findAll(Sort.by("fare").descending());
        Assert.assertEquals("Anay", passengerInfoList.get(0).getName());
    }

    /**
     * We can choose to return either a Page<T>, a Slice<T>, or a List<T> from any of our custom methods returning paginated data.
     */
    @Test
    public void testPagination() {
        //fetch first page with 2 rows
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        Page<PassengerInfo> passengerInfoList = passengerInfoRepository.findAll(firstPageWithTwoElements);
        Assert.assertEquals(2, passengerInfoList.getSize());
    }

    @Test
    public void testPaginationWithSort() {
        Pageable sortedByName = PageRequest.of(0, 3, Sort.by("name").descending());
        Slice<PassengerInfo> passengerInfoList = passengerInfoRepository.findAll(sortedByName);
        Assert.assertEquals(3, passengerInfoList.getSize());
        Assert.assertEquals("Anay", passengerInfoList.get().findFirst().get().getName());

        Pageable sortedByFareDescNameAsc =
                PageRequest.of(0, 5, Sort.by("fare").descending().and(Sort.by("name")));
    }



}
