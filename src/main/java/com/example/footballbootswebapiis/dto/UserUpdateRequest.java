package com.example.footballbootswebapiis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private Integer age;
    private String oldPassword;
    private String newPassword;
}
