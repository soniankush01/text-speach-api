package com.wellsfargo.textspeachapi.controller;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{uid}")
    public ResponseEntity findEmployee(@PathVariable("uid") String uid){
        Employee employee = employeeService.findEmployee(uid);
        if(null!=employee) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else {
            return new ResponseEntity("Not Found", HttpStatus.NO_CONTENT);
        }
    }

}
