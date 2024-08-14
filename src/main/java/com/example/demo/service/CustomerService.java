package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer saveOrUpdateCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id){
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(null);
       
    }

}
