package com.example.springdatajpademo.service;

import com.example.springdatajpademo.dto.Booking;
import com.example.springdatajpademo.dto.BookingSearchCriteria;
import com.example.springdatajpademo.entity.BookingInfo;
import com.example.springdatajpademo.error.BookingNotFoundException;
import com.example.springdatajpademo.repository.BookInfoSpecification;
import com.example.springdatajpademo.repository.BookingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager entityManager;

    private BookingInfo findById(Long id) {
       Optional<BookingInfo> result = bookingRepository.findById(id);
       result.orElseThrow(() -> new BookingNotFoundException(id));
       return result.get();
    }

    @Transactional(readOnly = true)
    public Booking findByBookingId(Long id) {
        BookingInfo entity = findById(id);
        return convertToBooking(entity);
    }

    private Booking convertToBooking(BookingInfo entity) {
        Booking booking = new Booking();
        BeanUtils.copyProperties(entity, booking);
        return booking;
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking updateBooking(Long bookingId, Booking booking) {
       BookingInfo entity = findById(bookingId);
       entity.updateBooking(booking);

       bookingRepository.save(entity);
       return convertToBooking(entity);
    }

    @Transactional(readOnly = true)
    public List<Booking> getBookingsByPassengerId(Long passengerId) {
        List<BookingInfo> entities = bookingRepository.findByPassengerInfoId(passengerId);
        return entities.stream().map(v -> convertToBooking(v)).collect(Collectors.toList());
    }


    public List<BookingInfo> getAllBookings(BookingSearchCriteria bookingSearchCriteria, Pageable pageRequest) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingSearchCriteria.getSource().ifPresent( value -> bookingInfo.setSource(value));

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Page<BookingInfo> pageResult = bookingRepository.findAll(Example.of(bookingInfo), pageRequest);
        return pageResult.stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookingsUsingCriteria(BookingSearchCriteria bookingSearchCriteria, Pageable pageRequest) {
        BookingInfo bookingInfo = new BookingInfo();
        bookingSearchCriteria.getSource().ifPresent( value -> bookingInfo.setSource(value));

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookingInfo> cq = builder.createQuery(BookingInfo.class);

        Root<BookingInfo> root = cq.from(BookingInfo.class);
        List<Predicate> predicates = new ArrayList<>();
        bookingSearchCriteria.getSource().ifPresent(
                value -> predicates.add(builder.equal(root.get("source"), value))
        );
        bookingSearchCriteria.getDestination().ifPresent(
                value -> predicates.add(builder.equal(root.get("destination"), value))
        );

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<BookingInfo> query = entityManager.createQuery(cq);
        List<BookingInfo> pageResult = query.getResultList();

        return pageResult.stream().map(v -> convertToBooking(v)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookingsUsingSpecification(BookingSearchCriteria bookingSearchCriteria, Pageable pageRequest) {
        Specification<BookingInfo> specification = BookInfoSpecification.convertToSpecification(bookingSearchCriteria);
        List<BookingInfo> result = bookingRepository.findAll(specification);

        return result.stream().map(v -> convertToBooking(v)).collect(Collectors.toList());
    }




}
