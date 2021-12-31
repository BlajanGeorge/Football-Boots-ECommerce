package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.customvalidators.EmailConstraint;
import com.example.footballbootswebapiis.customvalidators.GenderConstraint;
import com.example.footballbootswebapiis.customvalidators.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@Validated
public class UserCreateRequest {
    @NotBlank(message = "First name can't be blank.")
    @Size(max = 255, message = "First name can't be longer than 255 characters.")
    private String firstName;
    @NotBlank(message = "Last name can't be blank.")
    @Size(max = 255, message = "Last name can't be longer than 255 characters.")
    private String lastName;
    @NotBlank(message = "Email can't be blank.")
    @Size(max = 255, message = "Email can't be longer than 255 characters.")
    @EmailConstraint
    private String email;
    @NotBlank(message = "Gender can't be blank.")
    @GenderConstraint
    private String gender;
    @NotNull
    @Range(min = 14, max = 120, message = "Age must be between 14 and 120.")
    private Integer age;
    @NotBlank(message = "Password can't be blank.")
    @Size(max = 20, message = "Password can't be longer than 20 characters.")
    @Size(min = 8, message = "Password can't be shorter than 8 characters.")
    @PasswordConstraint
    private String password;
}
