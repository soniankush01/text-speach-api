package com.wellsfargo.textspeachapi.repository;

import com.wellsfargo.textspeachapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    public Employee findByUid(String uid);
   // public Employee findByEmail(String email);
    //public Employee findByEmployeeId(Integer employeeId);
}

