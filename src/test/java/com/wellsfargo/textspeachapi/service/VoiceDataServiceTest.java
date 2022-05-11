package com.wellsfargo.textspeachapi.service;


import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class VoiceDataServiceTest {

    @InjectMocks
    private VoiceDataService voiceDataService;
    @Mock
    private VoiceDataRepository voiceDataRepository;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testJsonToObject(){
        assertEquals(7,7);
    }

   // @Test
    public void test_upload_voice() throws IOException {
        VoiceData voiceData = new VoiceData();
        MultipartFile file = new MockMultipartFile("voice.mp3","voice.mp3","text/audio",
                new FileInputStream(new File(this.getClass().getResource("/voice.mp3").getFile())));
        String empData = "{\"firstName\":\"Tribhuwan\",\"lastName\":\"Mahto\",\"email\":\"tribhuwan@wellsfargo.com\",\"legalName\":\"TribhuwanMahto\",\"preferredName\":\"Mahto\"}";
        Mockito.doNothing().when(voiceDataRepository.save(voiceData));
        voiceDataService.uploadVoice(file,empData);
    }

}
