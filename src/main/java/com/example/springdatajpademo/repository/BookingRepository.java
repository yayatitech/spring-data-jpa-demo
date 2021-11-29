package com.example.springdatajpademo.repository;

import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.entity.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingInfo, Long>, JpaSpecificationExecutor<BookingInfo> {

    List<BookingInfo> findByTravelDate(LocalDate travelDate);

    //query association using associated entity name and property
    List<BookingInfo> findByPassengerInfoId(Long passengerId);

}
