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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private VoiceDataRepository voiceDataRepository;

    @Test
    public void get_employee_test(){
        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(8173);
        Mockito.when(voiceDataRepository.findById(8173)).thenReturn(Optional.of(voiceData));
        Optional<VoiceData> result  = employeeService.findEmployee(voiceData.getEmployeeId());
        assertEquals(Optional.of(8173),Optional.of(result.get().getEmployeeId()));
    }

    @Test
    public void test_update_profile_pic() throws IOException {
        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(123);

        MultipartFile file = new MockMultipartFile("image.jpg","image.jpg","text/image",
                new FileInputStream(this.getClass().getResource("/image.jpg").getFile()));
        String empData = "{\"employeeId\":\"123\"}";

        Mockito.when(voiceDataRepository.findById(123)).thenReturn(Optional.of(voiceData));
        Mockito.when(voiceDataRepository.save(any())).thenReturn(any());
        Optional<VoiceData> voiceData1 = employeeService.updateProfilePicture(empData,file);
        assertEquals(Optional.of(123).get(),voiceData1.get().getEmployeeId());
        verify(voiceDataRepository,times(1)).save(any());
    }
    @Test
    public void test_update_profile_pic_without_emp_record() throws IOException {
        VoiceData voiceData = null;

        MultipartFile file = new MockMultipartFile("image.jpg","image.jpg","text/image",
                new FileInputStream(this.getClass().getResource("/image.jpg").getFile()));
        String empData = "{\"employeeId\":\"123\"}";

        Mockito.when(voiceDataRepository.findById(123)).thenReturn(Optional.ofNullable(voiceData));
        assertNull(employeeService.updateProfilePicture(empData,file));

    }

    @Test
    public void get_profile_pic(){
        byte[] image = {1,2};
        VoiceData voiceData = new VoiceData();
        voiceData.setEmployeeId(8173);
        voiceData.setProfileImage(image);
        Mockito.when(voiceDataRepository.findById(8173)).thenReturn(Optional.of(voiceData));
        Optional<VoiceData> result  = employeeService.getProfilePicture(voiceData.getEmployeeId());
        assertEquals(Optional.of(8173),Optional.of(result.get().getEmployeeId()));
        assertEquals(Optional.of(image),Optional.of(result.get().getProfileImage()));
    }
    @Test
    public void test_when_no_profile_pic_present(){
        VoiceData voiceData = null;
        Mockito.when(voiceDataRepository.findById(8173)).thenReturn(Optional.ofNullable(voiceData));

        assertNull(employeeService.getProfilePicture(8173));

    }
}
