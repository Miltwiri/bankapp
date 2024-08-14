package com.example.bankapp.code.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bankapp.code.entities.Customer;
import com.example.bankapp.code.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String customerId, @RequestParam String pin) {
        return ResponseEntity.ok(customerService.login(customerId, pin));
    }

    @GetMapping("/{customerId}/balance")
    public ResponseEntity<Double> getCustomerBalance(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerBalance(customerId));
    }

    @GetMapping("/{customerId}/mini-statement")
    public ResponseEntity<?> getMiniStatement(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getMiniStatement(customerId));
    }
}
