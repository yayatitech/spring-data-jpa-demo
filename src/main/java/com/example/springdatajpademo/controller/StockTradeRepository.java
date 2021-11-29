package com.example.springdatajpademo.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockTradeRepository extends JpaRepository<StockTrade, Integer> {

    List<StockTrade> findByTypeAndUserId(String type, String userId);

}
