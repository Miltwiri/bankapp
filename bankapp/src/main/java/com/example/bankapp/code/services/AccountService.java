package com.example.bankapp.code.services;

public interface AccountService {
    Double deposit(String customerId, double amount);
    Double withdraw(String customerId, double amount);
    Double transfer(String customerId, String targetAccountId, double amount);
}