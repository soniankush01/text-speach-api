package com.wellsfargo.textspeachapi.controller;


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
                "/voice/upload").file(file)
                .file(employeeDate).contentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
