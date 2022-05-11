package com.wellsfargo.textspeachapi.controller;

import com.wellsfargo.textspeachapi.service.VoiceDataService;
import com.wellsfargo.textspeachapi.exception.ResourceNotFoundException;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.repository.VoiceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {
    @Autowired
    private VoiceDataRepository voiceDataRepository;

    @GetMapping("/users")
    public Page<VoiceData> getUsers(Pageable pageable) {
        return voiceDataRepository.findAll(pageable);
    }


    @PutMapping("/users/{userId}")
    public VoiceData updateUser(@PathVariable Long uid,
                                @RequestBody VoiceData voiceDataRequest) {
        return voiceDataRepository.findById(uid)
                .map(voiceData -> {
                    voiceData.setFirstName(voiceDataRequest.getFirstName());
                    voiceData.setLastName(voiceDataRequest.getLastName());
                    voiceData.setEmail(voiceDataRequest.getEmail());
                    return voiceDataRepository.save(voiceData);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
    }


    @DeleteMapping("/users/{uid}")
    public ResponseEntity<?> deleteUser(@PathVariable Long uid) {
        return voiceDataRepository.findById(uid)
                .map(voiceData -> {
                    voiceDataRepository.delete(voiceData);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + uid));
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestPart("employeeData") String employeeData) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       VoiceData voiceData =  VoiceDataService.getEmployee(employeeData);
        voiceData.setData(file.getBytes());
        voiceData.setFileName(fileName);
        voiceData.setFileType(file.getContentType());
       // Employee FileDB = new Employee(fileName, file.getContentType(), file.getBytes());
        String message = "";

        try {
            voiceDataRepository.save(voiceData);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/files/{uid}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long uid) {
        VoiceData voiceData = voiceDataRepository.findById(uid).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + voiceData.getFileName() + "\"")
                .body(voiceData.getData());
    }
}
