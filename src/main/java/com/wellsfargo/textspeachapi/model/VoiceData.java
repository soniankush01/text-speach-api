package com.wellsfargo.textspeachapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@DynamicUpdate(true)
@Table(name = "voice_data")
@Data
@AllArgsConstructor
public class VoiceData {

    @Id
    private Integer employeeId ;

    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String legalName;
    private String preferredName;
    private String fileName;
    private String fileType;
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;
    private boolean optIn;


    public VoiceData(String fileName, String fileType, byte[] bytes) {
        this.fileName =fileName;
        this.fileType = fileType;
        this.data = bytes;
    }

    public VoiceData(){

    }
}
