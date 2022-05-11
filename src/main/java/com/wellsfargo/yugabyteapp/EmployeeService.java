package com.wellsfargo.yugabyteapp;

import com.google.gson.Gson;
import com.wellsfargo.yugabyteapp.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    public static Employee getEmployee(String empData){
        Gson gson = new Gson();
        Employee employee = gson.fromJson(empData,Employee.class);
        return employee;

    }
}
