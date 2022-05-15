package com.wellsfargo.textspeachapi.controller;


import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
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
        VoiceData employee = new VoiceData();
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
        VoiceData employee = null;

        when(employeeService.findEmployee(12345)).thenReturn(Optional.ofNullable(employee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void find_picture_by_uid() throws Exception {
        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(12345);
        voiceData.setEmail("abc@wellsfargo.com");
        voiceData.setFirstName("John");
        voiceData.setLastName("Smith");
        voiceData.setLegalName("John Smith");
        voiceData.setPreferredName("John");
        byte[] byteArr = {1,2};
        voiceData.setProfileImage(byteArr);
        when(employeeService.getProfilePicture(voiceData.getEmployeeId())).thenReturn(Optional.of(voiceData));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/profilePicture/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    //@Test
    public void find_employee_picture_no_record() throws Exception {

       VoiceData employee=null;
        when(employeeService.getProfilePicture(12345)).thenReturn(Optional.ofNullable(employee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/profilePicture/12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_success_image_upload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("profileImage","image.jpeg", MediaType.MULTIPART_FORM_DATA_VALUE,"upload image".getBytes());
        MockMultipartFile employeeId = new MockMultipartFile("employeeId",null,"application/json",
                "{\"employeeId\":\"123\"}".getBytes());

        VoiceData voiceData = new VoiceData();
        byte[] byteArr = {1,2};
        voiceData.setProfileImage(byteArr);
        voiceData.setEmail("abc@test.com");
        voiceData.setOptIn(Boolean.TRUE);
        voiceData.setEmployeeId(12234);
        voiceData.setImageName("abc.jpg");
        Mockito.when(employeeService.updateProfilePicture("{\"employeeId\":\"123\"}", file)).thenReturn(Optional.of(voiceData));
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/employee/profilePicture");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(file)
                        .file(employeeId).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))

                .andExpect(status().isOk());
    }
    @Test
    public void test_image_upload_when_no_employee_found() throws Exception {
        MockMultipartFile file = new MockMultipartFile("profileImage","image.jpeg", MediaType.MULTIPART_FORM_DATA_VALUE,"upload image".getBytes());
        MockMultipartFile employeeId = new MockMultipartFile("employeeId",null,"application/json",
                "{\"employeeId\":\"123\"}".getBytes());


        Mockito.when(employeeService.updateProfilePicture("{\"employeeId\":\"123\"}", file)).thenReturn(null);
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/employee/profilePicture");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });
        mockMvc.perform(builder
                        .file(file)
                        .file(employeeId).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))

                .andExpect(status().is2xxSuccessful());
    }
    }
