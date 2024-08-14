package com.example.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Operation {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOp;
    private double amount;
    private opType type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)


    public BankAccount getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDateOp() {
        return dateOp;
    }
    public void setDateOp(Date dateOp) {
        this.dateOp = dateOp;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public opType getType() {
        return type;
    }
    public void setType(opType type) {
        this.type = type;
    }

    
}
