package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void get_employee_test(){
        Employee employee = new Employee();
        employee.setEmployeeId(8173);
        Mockito.when(employeeRepository.findById(8173)).thenReturn(Optional.of(employee));
        Optional<Employee> result  = employeeService.findEmployee(employee.getEmployeeId());
        assertEquals(Optional.of(8173),Optional.of(result.get().getEmployeeId()));
    }
}
