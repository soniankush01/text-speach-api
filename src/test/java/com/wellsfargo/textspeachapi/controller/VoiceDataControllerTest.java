package com.wellsfargo.textspeachapi.controller;


import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class VoiceDataControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoiceDataService voiceDataService;

    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new VoiceDataController(voiceDataService)).build();
    }

    @Test
    public void test_success_voice_upload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","voice.mp3", MediaType.MULTIPART_FORM_DATA_VALUE,"upload voice".getBytes());
        MockMultipartFile employeeDate = new MockMultipartFile("employeeData",null,"application/json",
                "{\"firstName\":\"Tribhuwan\",\"lastName\":\"Mahto\",\"email\":\"tribhuwan@wellsfargo.com\",\"legalName\":\"TribhuwanMahto\",\"preferredName\":\"Mahto\"}".getBytes());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(
                "/user/uploadRecord").file(file)
                .file(employeeDate).contentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void test_success_voice_upload_fail() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","voice.mp3", MediaType.MULTIPART_FORM_DATA_VALUE,"upload voice".getBytes());
        MockMultipartFile employeeDate = new MockMultipartFile("employeeData",null,"application/json",
                "".getBytes());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(
                        "/user/uploadRecord").file(file)
                .file(employeeDate).contentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void test_voice_download_with_status_ok() throws Exception {
        VoiceData voiceData =new VoiceData();
        byte[] byteArr = {1,2};
        voiceData.setData(byteArr);
        voiceData.setEmail("abc@test.com");
        voiceData.setOptIn(Boolean.TRUE);
        voiceData.setEmployeeId(12234);
        when(voiceDataService.downloadVoice("u12345")).thenReturn(voiceData);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getRecord/u12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    @Test
    public void test_voice_download_with_no_content() throws Exception {
        VoiceData voiceData = null;

        when(voiceDataService.downloadVoice("u12345")).thenReturn(voiceData);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getRecord/u12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void test_voice_download_with_optOut() throws Exception {
        VoiceData voiceData =new VoiceData();
        byte[] byteArr = {1,2};
        voiceData.setData(byteArr);
        voiceData.setEmail("abc@test.com");
        voiceData.setOptIn(Boolean.FALSE);
        voiceData.setEmployeeId(12234);
        when(voiceDataService.downloadVoice("u12345")).thenReturn(voiceData);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getRecord/u12345");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
    @Test
    public void test_optIn_update_success() throws Exception {


        when(voiceDataService.updateOptIn(12345,true)).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/updateOptIn")
                .param("employeeId", String.valueOf(12345)).header("optIn",true);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void test_optIn_update_when_no_employee_found() throws Exception {


        when(voiceDataService.updateOptIn(12345,true)).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/updateOptIn")
                .param("employeeId", String.valueOf(12345)).header("optIn",true);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }
}
