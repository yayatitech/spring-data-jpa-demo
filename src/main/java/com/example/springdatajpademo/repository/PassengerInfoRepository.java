package com.example.springdatajpademo.repository;

import com.example.springdatajpademo.entity.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


//we can also extend CrudRepository
@Repository
public interface PassengerInfoRepository extends JpaRepository<PassengerInfo, Long>, JpaSpecificationExecutor<PassengerInfo> {

    List<PassengerInfo> findByName(String name);

    //uses named query PassengerInfo.findByEmailAddress i.e. having same name
    PassengerInfo findByEmailAddress(String email);

    @Query(value = "SELECT p FROM PassengerInfo p WHERE p.city = 1 ") //value is either JPQL or SQL
    PassengerInfo findByCity(String city);

    @Query(value = "SELECT p FROM PASSENGER_INFO p WHERE p.city = 2 ", nativeQuery = true)
    List<PassengerInfo> findByCityNative(String city);

}
