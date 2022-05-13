package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
        employee.setUid("u8173");
        Mockito.when(employeeRepository.findByUid("u8173")).thenReturn(employee);
        Employee result  = employeeService.findEmployee(employee.getUid());
        assertEquals("u8173",result.getUid());
    }
}
