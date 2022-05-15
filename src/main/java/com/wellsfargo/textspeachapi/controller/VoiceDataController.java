package com.wellsfargo.textspeachapi.controller;

import com.google.gson.Gson;
import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.service.VoiceDataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
public class VoiceDataController {

    private final VoiceDataService voiceDataService;

    public VoiceDataController(VoiceDataService voiceDataService) {
        this.voiceDataService = voiceDataService;
    }

    @PostMapping(value = "/user/uploadRecord", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestPart("employeeData") String employeeData) throws IOException {
        String message = "";
        try {
            voiceDataService.uploadVoice(file, employeeData);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/user/getRecord/{input}")
    public ResponseEntity<byte[]> downloadVoice(@PathVariable String input) {
        VoiceData voiceData = voiceDataService.downloadVoice(input);
        if (null != voiceData) {

            if (voiceData.isOptIn()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "abc.mp3" + "\"")
                        .header("Access-Control-Expose-Headers", "empId")
                        .header("empId", voiceData.getEmployeeId().toString())
                        .body(voiceData.getData());
            } else {
                return ResponseEntity.ok()
                        .header("Access-Control-Expose-Headers", "empId")
                        .header("empId", voiceData.getEmployeeId().toString())
                        .body(null);
            }
        } else {
            return new ResponseEntity("Not Record Found", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "/user/updateOptIn")
    public ResponseEntity updateOptIn(@RequestParam("employeeId") Integer employeeId, @RequestHeader boolean optIn) {

        boolean status = voiceDataService.updateOptIn(employeeId,optIn);
        if(status){
            return new ResponseEntity("Record update successfully " , HttpStatus.OK);
        }else{
            return new ResponseEntity("Employee Id : " + employeeId + "Not found" , HttpStatus.NO_CONTENT);
        }

    }

}
