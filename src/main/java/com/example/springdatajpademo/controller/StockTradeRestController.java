package com.example.springdatajpademo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "trades")
public class StockTradeRestController {

    @Autowired
    private StockTradeRepository repository;

    @PostMapping
    public ResponseEntity<StockTrade> createNew(@RequestBody @Valid StockTrade stocktrade) {

        try {
            System.out.println("insert called");
            StockTrade result = repository.save(stocktrade);
            System.out.println("insert response = "+ result.getId());
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch(Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<StockTrade>> findById(@RequestParam(required = false) String type,
                                                     @RequestParam(required = false) String userId) {

        List<StockTrade> result = null;
        if(!StringUtils.isEmpty(type) && !StringUtils.isEmpty(userId)) {
            result = repository.findByTypeAndUserId(type, userId);
        } else {
            result = repository.findAll();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StockTrade> findById(@PathVariable Integer id) {

        Optional<StockTrade> result = repository.findById(id);

        if(result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateStock(@PathVariable Integer id) {
        return new ResponseEntity("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<String> patchStock(@PathVariable Integer id) {
        return new ResponseEntity("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return new ResponseEntity("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }




}
