package com.example.bankapp.code.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bankapp.code.services.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{customerId}/deposit")
    public ResponseEntity<Double> deposit(@PathVariable String customerId, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.deposit(customerId, amount));
    }

    @PostMapping("/{customerId}/withdraw")
    public ResponseEntity<Double> withdraw(@PathVariable String customerId, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.withdraw(customerId, amount));
    }

    @PostMapping("/{customerId}/transfer")
    public ResponseEntity<Double> transfer(@PathVariable String customerId, @RequestParam String targetAccountId, @RequestParam double amount) {
        return ResponseEntity.ok(accountService.transfer(customerId, targetAccountId, amount));
    }
}

