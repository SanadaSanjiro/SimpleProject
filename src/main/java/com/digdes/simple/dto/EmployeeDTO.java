package com.digdes.simple.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    Long id;
    String uid;
    String firstname;
    String lastname;
    String surname;
    String position;
    String account;
    String email;
    String status;
}
