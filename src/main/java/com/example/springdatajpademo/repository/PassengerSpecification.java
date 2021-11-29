package com.example.springdatajpademo.repository;

import com.example.springdatajpademo.dto.PassengerSearchCriteria;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.entity.PassengerInfo_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class PassengerSpecification {

    public static Specification<PassengerInfo> createSpecification(PassengerSearchCriteria passengerSearchCriteria) {

        return nameEqualTo(passengerSearchCriteria.getName())
                .and(cityEqualTo(passengerSearchCriteria.getCity()));

    }

    public static Specification<PassengerInfo> nameEqualTo(Optional<String> name) {

        return (root, query, builder) -> {
            return name.map(val -> builder.equal(root.get(PassengerInfo_.name), val))
                    .orElse(null);
        };
    }

    public static Specification<PassengerInfo> cityEqualTo(Optional<String> source) {

        return (root, query, builder) -> {
            return source.map(val -> builder.equal(root.get(PassengerInfo_.city), val))
                    .orElse(null);
        };
    }

}
