package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findEmployee(String input){
        return null;
    }
}
