package com.example.bankapp.code.services.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankapp.code.entities.Account;
import com.example.bankapp.code.entities.Transaction;
import com.example.bankapp.code.repositories.TransactionRepository;
import com.example.bankapp.code.services.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void recordTransaction(Account account, double amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<Transaction> getMiniStatement(Long accountId) {
        return transactionRepository.findTop10ByAccountIdOrderByTimestampDesc(accountId);
    }
}
