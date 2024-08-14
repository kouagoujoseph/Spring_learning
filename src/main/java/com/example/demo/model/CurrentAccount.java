package com.example.demo.model;

import jakarta.persistence.Entity;

@Entity
public class CurrentAccount extends BankAccount{

    private double overDraf;

    public double getOverDraf() {
        return overDraf;
    }

    public void setOverDraf(double overDraf) {
        this.overDraf = overDraf;
    }

}
