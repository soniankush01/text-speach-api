package com.wellsfargo.yugabyteapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
public class Employee {

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


    public Employee(String fileName, String fileType, byte[] bytes) {
        this.fileName =fileName;
        this.fileType = fileType;
        this.data = bytes;
    }

    public Employee(){

    }
}
