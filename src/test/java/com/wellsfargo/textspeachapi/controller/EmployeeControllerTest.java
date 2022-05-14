package com.wellsfargo.textspeachapi.controller;


import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.service.EmployeeService;
import com.wellsfargo.textspeachapi.service.VoiceDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeService)).build();
    }
    @Test
    public void find_employee_by_uid() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(12345);
        employee.setEmail("abc@wellsfargo.com");
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setLegalName("John Smith");
        employee.setPreferredName("John");
        when(employeeService.findEmployee(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    @Test
    public void find_employee_by_uid_no_record() throws Exception {
        Employee employee = null;

        when(employeeService.findEmployee(12345)).thenReturn(Optional.ofNullable(employee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void find_picture_by_uid() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(12345);
        employee.setEmail("abc@wellsfargo.com");
        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setLegalName("John Smith");
        employee.setPreferredName("John");
        byte[] byteArr = {1,2};
        employee.setProfileImage(byteArr);
        when(employeeService.getProfilePicture(employee.getEmployeeId())).thenReturn(Optional.of(employee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/profilePicture/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
   // @Test
    public void find_employee_picture_no_record() throws Exception {

       Employee employee=null;
        when(employeeService.getProfilePicture(12345)).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/profilePicture/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }
    }
