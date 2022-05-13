package com.wellsfargo.textspeachapi.service;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
}
