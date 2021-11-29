package com.example.springdatajpademo.entity;

import com.example.springdatajpademo.dto.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Lombok;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
/* TODO how to add assocation in the unique key */
//@Table(name = "BOOKING_INFO", uniqueConstraints = { @UniqueConstraint(name = "uniqueSourceAndTravelDate", columnNames = {"source", "travelDate", "passengerInfo"})})
public class BookingInfo {

    @Id
    @GeneratedValue
    public Long id;

    private String source;
    private String destination;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate travelDate;
    private double fare;

    @ManyToOne
    // this solved No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer
    //https://stackoverflow.com/questions/52656517/no-serializer-found-for-class-org-hibernate-proxy-pojo-bytebuddy-bytebuddyinterc
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @JsonIgnore
    private PassengerInfo passengerInfo;

    public void updateBooking(Booking bookingInfo) {
        this.travelDate = bookingInfo.getTravelDate();
        this.fare = bookingInfo.getFare();
    }
}
