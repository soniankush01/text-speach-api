package com.wellsfargo.textspeachapi.service;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        voiceDataRepository.save(voiceData);
    }

    public VoiceData downloadVoice(Long uid){
        return voiceDataRepository.findById(uid).get();
    }

    public static VoiceData getEmployee(String empData){
        Gson gson = new Gson();
        VoiceData voiceData = gson.fromJson(empData, VoiceData.class);
        return voiceData;

    }
}
