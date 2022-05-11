package com.wellsfargo.textspeachapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "voice_data")
@Data
@AllArgsConstructor
public class VoiceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long uid;
    private String firstName;
    private String lastName;
    private String email;
    private String legalName;
    private String preferredName;
    private String fileName;
    private String fileType;
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;


    public VoiceData(String fileName, String fileType, byte[] bytes) {
        this.fileName =fileName;
        this.fileType = fileType;
        this.data = bytes;
    }

    public VoiceData(){

    }
}
