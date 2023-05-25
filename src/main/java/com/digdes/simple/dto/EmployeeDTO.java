package com.digdes.simple.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    Long id;
    String uid;
    String firstName;
    String lastName;
    String surName;
    String position;
    String account;
    String email;
    String status;
}
