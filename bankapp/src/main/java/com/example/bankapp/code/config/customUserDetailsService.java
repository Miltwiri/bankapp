package com.example.bankapp.code.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bankapp.code.entities.Customer;
import com.example.bankapp.code.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class customUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByCustomerId(customerId);

        if (!customerOptional.isPresent()) {
            throw new UsernameNotFoundException("Customer not found");
        }

        Customer customer = customerOptional.get();
        return new org.springframework.security.core.userdetails.User(customer.getCustomerId(), customer.getPin(), new ArrayList<>());
    }
}

