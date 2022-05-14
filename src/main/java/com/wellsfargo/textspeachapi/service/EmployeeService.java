package com.wellsfargo.textspeachapi.service;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
     }

    public Optional<Employee> findEmployee(Integer employeeId){
        return employeeRepository.findById(employeeId)
                .map(e->{
                    Employee employee = new Employee();
                    employee.setEmployeeId(e.getEmployeeId());
                    employee.setUid(e.getUid());
                    employee.setEmail(e.getEmail());
                    employee.setFirstName(e.getFirstName());
                    employee.setLastName(e.getLastName());
                    employee.setPreferredName(e.getPreferredName());
                    employee.setLegalName(e.getLegalName());
                    return employee;
                });


    }

    public Optional<Employee> updateProfilePicture(String employeeId, MultipartFile image) throws IOException {
        String fileName  = StringUtils.cleanPath(image.getOriginalFilename());
        Gson gson = new Gson();
        Employee employee = gson.fromJson(employeeId,Employee.class);
       Optional<Employee> emp = employeeRepository.findById(employee.getEmployeeId());
        if(emp.isPresent()){
            emp.get().setProfileImage(image.getBytes());
            emp.get().setImageName(fileName);
            employeeRepository.save(emp.get());
        }else{
            return null;
        }
        return emp;
    }

    public Optional<Employee> getProfilePicture(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()){
            return employee;
        }else{
            return null;
        }

    }
}


