package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
    private double salary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_tasks",
                       joinColumns = @JoinColumn(name="employee_id"),
                       inverseJoinColumns = @JoinColumn(name="tasks_id"))
    private Set<Task> tasks= new HashSet<>();
    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() { 
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

}
