package com.shel.renter.repository;

import com.shel.renter.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAllByIsActiveTrue();
    Optional<Customer> findCustomerById(Long id);
}
