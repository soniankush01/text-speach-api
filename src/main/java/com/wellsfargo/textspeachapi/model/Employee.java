package com.wellsfargo.textspeachapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@DynamicUpdate(true)
@Table(name = "employee")
@Data
@AllArgsConstructor
public class Employee {

    private String uid;
    @Id
    private Integer employeeId ;
    private String firstName;
    private String lastName;
    private String email;
    private String legalName;
    private String preferredName;
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] profileImage;
    private String imageName;

    public Employee(){}
}
