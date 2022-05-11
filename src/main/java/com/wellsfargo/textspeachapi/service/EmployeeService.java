package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
     }

    public Employee findEmployee(String input){
        //uid
        if(input.startsWith("u")){
            return employeeRepository.findByUid(input);
        }//email
        else if(input.startsWith("e")){
            input = input.substring(1);
            return employeeRepository.findByEmail(input);
        }// emp id
        else if(input.startsWith("ei")){
            input = input.substring(2);
            Integer empId = Integer.parseInt(input);
            return employeeRepository.findByEmployeeId(empId);
        }else{
            return null;
        }
    }
}
