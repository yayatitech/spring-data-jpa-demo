package com.example.springdatajpademo.dto;

import com.example.springdatajpademo.error.IpAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long paymentId;
    private String accountNo;
    private double amount;
    private String cardType;

    @IpAddress
    private String ipAddress;

}
