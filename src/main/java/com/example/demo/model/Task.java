package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
   
    
    @ManyToMany(mappedBy = "tasks")
    private Set<Employee> employee=new HashSet<>();

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    
    public Set<Employee> getEmployees(){
     return employee;
    }
    public void setEmployee(Set<Employee> employee){
        this.employee=employee;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
