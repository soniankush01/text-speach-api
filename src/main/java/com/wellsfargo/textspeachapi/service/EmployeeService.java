package com.wellsfargo.textspeachapi.service;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.EmployeeRepository;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeService {

    private final VoiceDataRepository voiceDataRepository;

    @Autowired
    public EmployeeService(VoiceDataRepository voiceDataRepository) {
        this.voiceDataRepository = voiceDataRepository;
     }

    public Optional<Employee> findEmployee(Integer employeeId){
        return voiceDataRepository.findById(employeeId)
                .map(e->{
                    Employee employee = new Employee();
                    employee.setEmployeeId(e.getEmployeeId());
                    employee.setUid(e.getUid());
                    employee.setEmail(e.getEmail());
                    employee.setFirstName(e.getFirstName());
                    employee.setLastName(e.getLastName());
                    employee.setPreferredName(e.getPreferredName());
                    employee.setLegalName(e.getLegalName());
                    return employee;
                });


    }

    public Optional<VoiceData> updateProfilePicture(String employeeId, MultipartFile image) throws IOException {
        String fileName  = StringUtils.cleanPath(image.getOriginalFilename());
        Gson gson = new Gson();
        Employee employee = gson.fromJson(employeeId,Employee.class);
       Optional<VoiceData> emp = voiceDataRepository.findById(employee.getEmployeeId());
        if(emp.isPresent()){
            emp.get().setProfileImage(image.getBytes());
            emp.get().setImageName(fileName);
            voiceDataRepository.save(emp.get());
        }else{
            return null;
        }
        return emp;
    }

    public Optional<VoiceData> getProfilePicture(Integer employeeId) {
        Optional<VoiceData> voiceData = voiceDataRepository.findById(employeeId);
        if(voiceData.isPresent()){
            return voiceData;
        }else{
            return null;
        }

    }
}


