package com.example.springdatajpademo.entity;


import com.example.springdatajpademo.error.IpAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT_INFO")
public class PaymentInfo {

    @Id
    //@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_gen")
    @SequenceGenerator(name = "pay_gen", sequenceName = "pay_seq", allocationSize = 12)
    private Long paymentId;
    private String accountNo;
    private double amount;
    private String cardType;

    @IpAddress
    private String ipAddress;

    @OneToOne
    private PassengerInfo passengerInfo;


}
