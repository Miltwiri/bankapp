package com.example.bankapp.code.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bankapp.code.config.JwtTokenProvider;
import com.example.bankapp.code.entities.Customer;
import com.example.bankapp.code.entities.Transaction;
import com.example.bankapp.code.repositories.CustomerRepository;
import com.example.bankapp.code.repositories.TransactionRepository;
import com.example.bankapp.code.services.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer registerCustomer(Customer customer) {
        // Generate random PIN
        String randomPin = String.valueOf((int)(Math.random() * 9000) + 1000);
        customer.setPin(passwordEncoder.encode(randomPin));

        // Save customer with initial account
        return customerRepository.save(customer);
    }

    @Override
    public String login(String customerId, String pin) {
        Optional<Customer> customerOpt = customerRepository.findByCustomerId(customerId);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (passwordEncoder.matches(pin, customer.getPin())) {
                return jwtTokenProvider.generateToken(customer.getCustomerId());
            }
        }
        throw new RuntimeException("Invalid customer ID or PIN");
    }

    @Override
    public Double getCustomerBalance(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getAccount().getBalance().doubleValue();
    }

    @Override
    public List<Transaction> getMiniStatement(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return transactionRepository.findTop10ByAccountIdOrderByTimestampDesc(customer.getAccount().getId());
    }
}
