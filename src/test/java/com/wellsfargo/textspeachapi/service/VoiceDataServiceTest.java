package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VoiceDataServiceTest {

    @InjectMocks
    private VoiceDataService voiceDataService;
    @Mock
    private VoiceDataRepository voiceDataRepository;

     @Test
    public void test_upload_voice() throws IOException {

        MultipartFile file = new MockMultipartFile("voice.mp3","voice.mp3","text/audio",
                new FileInputStream(this.getClass().getResource("/voice.mp3").getFile()));
        String empData = "{\"firstName\":\"Tribhuwan\",\"lastName\":\"Mahto\",\"email\":\"tribhuwan@wellsfargo.com\",\"legalName\":\"TribhuwanMahto\",\"preferredName\":\"Mahto\"}";

        Mockito.when(voiceDataRepository.save(any())).thenReturn(any());
        voiceDataService.uploadVoice(file,empData);
        verify(voiceDataRepository,times(1)).save(any());
    }

    @Test
    public void test_download_voice_by_employeeId() {

        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(12345);
        voiceData.setEmail("abc@wellsfargo.com");
        voiceData.setFirstName("John");
        voiceData.setLastName("Smith");
        voiceData.setLegalName("John Smith");
        voiceData.setPreferredName("John");
        byte[] byteArr = {1,2};
        voiceData.setData(byteArr);
        Mockito.when(voiceDataRepository.findByEmployeeId(12345)).thenReturn(voiceData);
        VoiceData voiceData1 = voiceDataService.downloadVoice("12345");
        assertEquals(byteArr,voiceData1.getData());

    }
    @Test
    public void test_download_voice_by_uid() {

        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(12345);
        voiceData.setEmail("abc@wellsfargo.com");
        voiceData.setFirstName("John");
        voiceData.setLastName("Smith");
        voiceData.setLegalName("John Smith");
        voiceData.setPreferredName("John");
        voiceData.setUid("u123");
        byte[] byteArr = {1,2};
        voiceData.setData(byteArr);
        Mockito.when(voiceDataRepository.findByUid("u123")).thenReturn(voiceData);
        VoiceData voiceData1 = voiceDataService.downloadVoice("u123");
        assertEquals("u123",voiceData1.getUid());

    }

    @Test
    public void test_download_voice_by_email() {

        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(12345);
        voiceData.setEmail("abc@wellsfargo.com");
        voiceData.setFirstName("John");
        voiceData.setLastName("Smith");
        voiceData.setLegalName("John Smith");
        voiceData.setPreferredName("John");
        voiceData.setUid("u123");
        byte[] byteArr = {1,2};
        voiceData.setData(byteArr);
        Mockito.when(voiceDataRepository.findByEmail("abc@wellsfargo.com")).thenReturn(voiceData);
        VoiceData voiceData1 = voiceDataService.downloadVoice("abc@wellsfargo.com");
        assertEquals(byteArr,voiceData1.getData());

    }

    @Test
    public void test_download_voice_without_record() {

        Mockito.when(voiceDataRepository.findByEmail("abc@wellsfargo.com")).thenReturn(null);
         assertNull(voiceDataService.downloadVoice("abc@wellsfargo.com"));


    }
}
