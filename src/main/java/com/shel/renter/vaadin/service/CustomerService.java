package com.shel.renter.vaadin.service;

import com.shel.renter.entity.Customer;
import com.shel.renter.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public List<Customer> getAllActiveCustomers() {
        return customerRepository.findAllByIsActiveTrue();
    }

    public Optional<Customer> getCustomerInfo(Long id) {
        return customerRepository.findCustomerById(id);
    }
}
