package com.example.bankapp.code.services;

import java.util.List;

import com.example.bankapp.code.entities.Customer;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    String login(String customerId, String pin);
    Double getCustomerBalance(String customerId);
    List<?> getMiniStatement(String customerId);
}