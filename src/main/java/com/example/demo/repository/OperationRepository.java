package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Operation;

public interface OperationRepository extends JpaRepository<Operation ,Long>{
    
    List<Operation> findByBankAccountId(String bankAccountId);

}
