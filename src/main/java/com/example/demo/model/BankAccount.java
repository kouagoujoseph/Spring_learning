package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class BankAccount {
@Id
protected String id;
protected Date createdAt;
protected Double balance;
protected AccStatus status;
protected String currency;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "customer_id")
private Customer customer;
@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)

public Customer getCustomer() {
    return customer;
}
public void setCustomer(Customer customer) {
    this.customer = customer;
}


@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<Operation> operations=new HashSet<>();

@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
public Set<Operation> getOperations() {   
    return operations;
}
public void setOperations(Set<Operation> operations) {
    this.operations = operations;
}


public AccStatus getStatus() {
    return status;
}
public void setStatus(AccStatus status) {
    this.status = status;
}
public String getCurrency() {
    return currency;
}
public void setCurrency(String currency) {
    this.currency = currency;
}
public String getId() {
    return id;
}
public void setId(String id) {
    this.id = id;
}
public Date getCreatedAt() {
    return createdAt;
}
public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
}
public Double getBalance() {
    return balance;
}
public void setBalance(Double balance) {
    this.balance = balance;
}

}
