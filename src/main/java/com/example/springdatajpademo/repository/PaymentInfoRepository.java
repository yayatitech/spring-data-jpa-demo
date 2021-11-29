package com.example.springdatajpademo.repository;

import com.example.springdatajpademo.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String> {

    @Override
    PaymentInfo getById(String s);

    @Override
    Optional<PaymentInfo> findById(String s);


}
