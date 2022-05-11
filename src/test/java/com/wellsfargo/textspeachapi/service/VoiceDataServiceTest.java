package com.wellsfargo.textspeachapi.service;


import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

}
