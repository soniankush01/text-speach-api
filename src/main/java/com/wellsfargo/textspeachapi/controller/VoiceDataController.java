package com.wellsfargo.textspeachapi.controller;

import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.service.VoiceDataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class VoiceDataController {

    private final VoiceDataService voiceDataService;

    public VoiceDataController(VoiceDataService voiceDataService) {
        this.voiceDataService = voiceDataService;
    }

    @PostMapping(value = "/voice/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestPart("employeeData") String employeeData) throws IOException {


        String message = "";

        try {
            voiceDataService.uploadVoice(file,employeeData);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/voice/{uid}")
    public ResponseEntity<byte[]> downloadVoice(@PathVariable Long uid) {
        VoiceData voiceData = voiceDataService.downloadVoice(uid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + voiceData.getFileName() + "\"")
                .body(voiceData.getData());
    }
}
