package com.wellsfargo.textspeachapi.service;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final VoiceDataRepository voiceDataRepository;

    @Autowired
    public EmployeeService(VoiceDataRepository voiceDataRepository) {
        this.voiceDataRepository = voiceDataRepository;
     }

    public Optional<VoiceData> findEmployee(Integer employeeId){
        return voiceDataRepository.findById(employeeId)
                .map(e->{
                    VoiceData voiceData = new VoiceData();
                    voiceData.setEmployeeId(e.getEmployeeId());
                    voiceData.setUid(e.getUid());
                    voiceData.setEmail(e.getEmail());
                    voiceData.setFirstName(e.getFirstName());
                    voiceData.setLastName(e.getLastName());
                    voiceData.setPreferredName(e.getPreferredName());
                    voiceData.setLegalName(e.getLegalName());
                    voiceData.setOptIn(e.isOptIn());
                    voiceData.setCountry(e.getCountry());
                    return voiceData;
                });


    }

    public Optional<VoiceData> updateProfilePicture(String employeeId, MultipartFile image) throws IOException {
        String fileName  = StringUtils.cleanPath(image.getOriginalFilename());
        Gson gson = new Gson();
        VoiceData employee = gson.fromJson(employeeId,VoiceData.class);
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


