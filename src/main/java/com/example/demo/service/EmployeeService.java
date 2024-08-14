package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.Task;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.TaskRepository;

import java.util.List;
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Employee addTaskToEmployee(Long employeeId, Long taskId){
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        Task task=taskRepository.findById(taskId).orElse(null);

        if (employee != null && task != null) {
            employee.getTasks().add(task);
            employeeRepository.save(employee);
        }

        return employee; 
    }
    

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
    public Employee saveOrUpdateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

}
