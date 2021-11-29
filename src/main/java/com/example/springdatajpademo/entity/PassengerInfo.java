package com.example.springdatajpademo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PASSENGER_INFO")
//named query should be named this way with entity prefix so derived query can work
@NamedQueries(@NamedQuery(name="PassengerInfo.findByEmailAddress", query = "select p from PassengerInfo p where p.email = ?1"))
public class PassengerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pass_gen")
    @SequenceGenerator(name = "pass_gen", sequenceName = "pass_seq", allocationSize = 50) //allocation size match with db sequence
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String address;
    private String city;
    private String country;

    //this is needed else while returning this entity in rest response Jackson serializer will cause stack overflow error
    @JsonIgnore
    @OneToMany(mappedBy="passengerInfo", fetch = FetchType.LAZY)
    private List<BookingInfo> bookings;


}
