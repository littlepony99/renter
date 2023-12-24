package com.shel.renter.vaadin.service;

import com.shel.renter.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private Customer memoryCustomer;


    public Customer getCurrentUser() {
        return memoryCustomer;
//        return Customer.builder()
//                .id(1L)
//                .name("SERHII")
//                .surname("PLATONOV")
//                .phoneNumber("+380501234585")
//                .isActive(Boolean.TRUE)
//                .rentCount(15)
//                .build();
    }

    public void setCurrentUser(Customer customer) {
        memoryCustomer = customer;
    }
}
