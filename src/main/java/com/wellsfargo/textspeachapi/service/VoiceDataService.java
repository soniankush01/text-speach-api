package com.wellsfargo.textspeachapi.service;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class VoiceDataService {

    private final VoiceDataRepository voiceDataRepository;

    @Autowired
    public VoiceDataService(VoiceDataRepository voiceDataRepository) {
        this.voiceDataRepository = voiceDataRepository;
    }


    public void uploadVoice(MultipartFile file, String employeeData) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        VoiceData voiceData =  getEmployee(employeeData);
        voiceData.setData(file.getBytes());
        voiceData.setFileName(fileName);
        voiceData.setFileType(file.getContentType());
        voiceData.setOptIn(Boolean.TRUE);
        voiceDataRepository.save(voiceData);
    }

    public VoiceData downloadVoice(String input){
        //uid
        if(input.matches(".*[a-zA-Z].*") && input.matches(".*[0-9].*")){
            VoiceData voiceData = voiceDataRepository.findByUid(input);
            VoiceData voicePrint = getVoiceData(voiceData);
            if (voicePrint != null) return voicePrint;
        }//email
        else if(input.endsWith(".com")){

            VoiceData voiceData = voiceDataRepository.findByEmail(input);
            VoiceData voicePrint = getVoiceData(voiceData);
            if (voicePrint != null) return voicePrint;
        }// emp id
        else if(input.matches("^[0-9]*$")){

            Integer empId = Integer.parseInt(input);
            VoiceData voiceData =  voiceDataRepository.findByEmployeeId(empId);
            VoiceData voicePrint = getVoiceData(voiceData);
            if (voicePrint != null) return voicePrint;

        }

        return null;
    }



    public static VoiceData getEmployee(String empData){
        Gson gson = new Gson();
        try {
            VoiceData voiceData = gson.fromJson(empData, VoiceData.class);
            return voiceData;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    private VoiceData getVoiceData(VoiceData voiceData) {
        if(null!= voiceData){

                VoiceData voiceData1 = new VoiceData();
                voiceData1.setEmail(voiceData.getEmail());
                voiceData1.setEmployeeId(voiceData.getEmployeeId());
                voiceData1.setLegalName(voiceData.getLegalName());
                voiceData1.setFirstName(voiceData.getFirstName());
                voiceData1.setUid(voiceData.getUid());
                voiceData1.setLastName(voiceData.getLastName());
                voiceData1.setPreferredName(voiceData.getPreferredName());
                voiceData1.setOptIn(voiceData.isOptIn());
                voiceData1.setFileName(voiceData.getFileName());
                voiceData1.setData(voiceData.getData());
                return voiceData1;
             }
        return null;
    }

    public boolean updateOptIn(Integer employeeId, boolean optIn) {
        VoiceData voiceData = voiceDataRepository.findById(employeeId).get();
        if(null !=voiceData){
            voiceData.setOptIn(optIn);
             voiceDataRepository.save(voiceData);
             return true;
        }else{
            return false;
        }

    }
}
