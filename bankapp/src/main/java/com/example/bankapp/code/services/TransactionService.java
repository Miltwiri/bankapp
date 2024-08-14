package com.example.bankapp.code.services;

import java.util.List;

import com.example.bankapp.code.entities.Account;
import com.example.bankapp.code.entities.Transaction;

public interface TransactionService {
    void recordTransaction(Account account, double amount, String type);
    Transaction getTransactionById(String transactionId);
    List<Transaction> getMiniStatement(Long accountId);
}