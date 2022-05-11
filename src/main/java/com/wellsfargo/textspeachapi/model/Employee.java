package com.wellsfargo.textspeachapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
public class Employee {

    private String uid;
    private Integer employeeId ;
    private String firstName;
    private String lastName;
    private String email;
    private String legalName;
    private String preferredName;
}