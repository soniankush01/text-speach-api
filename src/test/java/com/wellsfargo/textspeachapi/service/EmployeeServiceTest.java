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

import java.util.Optional;

import static org.junit.Assert.assertEquals;

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
        Optional<Employee> result  = employeeService.findEmployee(voiceData.getEmployeeId());
        assertEquals(Optional.of(8173),Optional.of(result.get().getEmployeeId()));
    }
}
