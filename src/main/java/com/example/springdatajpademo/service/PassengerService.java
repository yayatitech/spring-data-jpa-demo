package com.example.springdatajpademo.service;

import com.example.springdatajpademo.dto.PassengerSearchCriteria;
import com.example.springdatajpademo.entity.PassengerInfo;
import com.example.springdatajpademo.repository.PassengerInfoRepository;
import com.example.springdatajpademo.repository.PassengerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PassengerService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PassengerInfoRepository passengerInfoRepository;

    @Transactional(readOnly = true)
    public PassengerInfo getPassengerById(Long id) {
        Optional<PassengerInfo> passengerInfo = passengerInfoRepository.findById(id);
        if (!passengerInfo.isPresent())
            throw new EntityNotFoundException("No passenger found with id " + id);
        return passengerInfo.get();
    }

    @Transactional(readOnly = true)
    public List<PassengerInfo> getPassengerByCity(String city) {
         return passengerInfoRepository.findByCityNative(city);
    }

    public void deletePassenger(Long id) {
        Optional<PassengerInfo> passengerInfo = passengerInfoRepository.findById(id);
        if (!passengerInfo.isPresent()) {
            throw new EntityNotFoundException("No record found");
        }
        passengerInfoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PassengerInfo> search(PassengerSearchCriteria searchCriteria, Integer page, Integer pageSize, String sortBy) {
        Specification<PassengerInfo> specification = PassengerSpecification.createSpecification(searchCriteria);

        if (page != null && sortBy != null) {
            PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy));
            Page resultPage = passengerInfoRepository.findAll(specification, pageRequest);
            return resultPage.getContent();
        }

        return passengerInfoRepository.findAll(specification);
    }


}
