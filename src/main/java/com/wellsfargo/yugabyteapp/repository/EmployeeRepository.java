package com.wellsfargo.yugabyteapp.repository;

import com.wellsfargo.yugabyteapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
