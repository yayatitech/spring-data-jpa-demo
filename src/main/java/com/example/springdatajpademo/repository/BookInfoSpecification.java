package com.example.springdatajpademo.repository;

import com.example.springdatajpademo.dto.BookingSearchCriteria;
import com.example.springdatajpademo.entity.BookingInfo;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class BookInfoSpecification {

    public static Specification convertToSpecification(BookingSearchCriteria bookingSearchCriteria) {
        return sourceEquals(bookingSearchCriteria.getSource()).and(destinationEquals(bookingSearchCriteria.getDestination()));
    }

    public static Specification<BookingInfo> sourceEquals(Optional<String> source) {
        return (bookInfo, cq, cb) -> source.map(value -> cb.equal(bookInfo.get("source"), value))
                .orElse(null);

    }

    public static Specification<BookingInfo> destinationEquals(Optional<String> destination) {
        return (bookInfo, cq, cb) -> destination.map(value -> cb.equal(bookInfo.get("destination"), value))
                .orElse(null);
    }
}
