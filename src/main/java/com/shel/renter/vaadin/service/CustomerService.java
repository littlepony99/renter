package com.shel.renter.vaadin.service;

import com.shel.renter.entity.Customer;
import com.shel.renter.repository.CustomerRepository;
import com.shel.renter.vaadin.util.PasswordCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

//    public Optional<Customer> getCustomerInfo(Long id) {
//        return customerRepository.findCustomerById(id);
//    }

    public Long login(String login, String password){
        Optional<Customer> optionalCustomer = customerRepository.findFirstByPhoneNumber(login);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            boolean validPass = PasswordCrypto.checkPassword(password, customer.getPassword());
            if(validPass){
                return customer.getId();
            }
        }
        return null;
    }
}
