package com.example.bankapp.code.services.impl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankapp.code.entities.Account;
import com.example.bankapp.code.entities.Customer;
import com.example.bankapp.code.repositories.AccountRepository;
import com.example.bankapp.code.repositories.CustomerRepository;
import com.example.bankapp.code.services.AccountService;
import com.example.bankapp.code.services.TransactionService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Double deposit(String customerId, double amount) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = customer.getAccount();
        account.setBalance(account.getBalance().add(BigDecimal.valueOf(amount)));
        accountRepository.save(account);

        transactionService.recordTransaction(account, amount, "DEPOSIT");
        return account.getBalance().doubleValue();
    }

    @Override
    public Double withdraw(String customerId, double amount) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account account = customer.getAccount();

        if (account.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(BigDecimal.valueOf(amount)));
        accountRepository.save(account);

        transactionService.recordTransaction(account, amount, "WITHDRAWAL");
        return account.getBalance().doubleValue();
    }

    @Override
    public Double transfer(String customerId, String targetAccountId, double amount) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Account sourceAccount = customer.getAccount();

        Account targetAccount = accountRepository.findById(Long.parseLong(targetAccountId))
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (sourceAccount.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
        targetAccount.setBalance(targetAccount.getBalance().add(BigDecimal.valueOf(amount)));

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        transactionService.recordTransaction(sourceAccount, amount, "TRANSFER");
        transactionService.recordTransaction(targetAccount, amount, "TRANSFER");

        return sourceAccount.getBalance().doubleValue();
    }
}