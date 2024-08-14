package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CurrentAccount;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, String>{

}
