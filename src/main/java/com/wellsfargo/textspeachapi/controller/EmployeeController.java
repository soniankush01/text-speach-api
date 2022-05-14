package com.wellsfargo.textspeachapi.controller;

import com.wellsfargo.textspeachapi.model.Employee;
import com.wellsfargo.textspeachapi.model.VoiceData;
import com.wellsfargo.textspeachapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity findEmployee(@PathVariable("employeeId") Integer employeeId){
        Optional<Employee> employee = employeeService.findEmployee(employeeId);
        if(employee.isPresent()) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else {
            return new ResponseEntity("Not Found", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "employee/profilePicture",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<byte[]> updateProfilePicture(@RequestPart("employeeId") String employeeId,
                                                       @RequestPart(value = "profileImage") MultipartFile profileImage) throws IOException {

        Optional<VoiceData> voiceData = employeeService.updateProfilePicture(employeeId,profileImage);
        if(null != voiceData){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + voiceData.get().getImageName() + "\"")

                    .body(voiceData.get().getProfileImage());

        }else{
            return new ResponseEntity("No profile found " , HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("employee/profilePicture/{employeeId}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Integer employeeId) {

        VoiceData voiceData = employeeService.getProfilePicture(employeeId).get();
        if (null != voiceData) {

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +voiceData.getImageName() + "\"")

                    .body(voiceData.getProfileImage());
        } else {
            return new ResponseEntity("Not profile Image Found", HttpStatus.NO_CONTENT);
        }
    }


}
