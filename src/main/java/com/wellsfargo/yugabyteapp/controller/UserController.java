package com.wellsfargo.yugabyteapp.controller;

import com.wellsfargo.yugabyteapp.EmployeeService;
import com.wellsfargo.yugabyteapp.exception.ResourceNotFoundException;
import com.wellsfargo.yugabyteapp.model.Employee;
import com.wellsfargo.yugabyteapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/users")
    public Page<Employee> getUsers(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }


    @PutMapping("/users/{userId}")
    public Employee updateUser(@PathVariable Long uid,
                               @RequestBody Employee employeeRequest) {
        return employeeRepository.findById(uid)
                .map(employee -> {
                    employee.setFirstName(employeeRequest.getFirstName());
                    employee.setLastName(employeeRequest.getLastName());
                    employee.setEmail(employeeRequest.getEmail());
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
    }


    @DeleteMapping("/users/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable Long uid) {
        return employeeRepository.findById(uid)
                .map(employee -> {
                    employeeRepository.delete(employee);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestPart("employeeData") String employeeData) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       Employee employee =  EmployeeService.getEmployee(employeeData);
        employee.setData(file.getBytes());
        employee.setFileName(fileName);
        employee.setFileType(file.getContentType());
       // Employee FileDB = new Employee(fileName, file.getContentType(), file.getBytes());
        String message = "";

        try {
            employeeRepository.save(employee);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files/{uid}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long uid) {
        Employee employee = employeeRepository.findById(uid).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + employee.getFileName() + "\"")
                .body(employee.getData());
    }
}
