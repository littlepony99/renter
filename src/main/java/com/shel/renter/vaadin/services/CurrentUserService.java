package com.shel.renter.vaadin.services;

import com.shel.renter.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public Customer getCurrentUserId() {
        return Customer.builder()
                .id(1L)
                .name("SERHII")
                .surname("PLATONOV")
                .phoneNumber("+380501234585")
                .isActive(Boolean.TRUE)
                .rentCount(15)
                .build();
    }
}
