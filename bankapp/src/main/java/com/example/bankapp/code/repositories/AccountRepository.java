package com.example.bankapp.code.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankapp.code.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
