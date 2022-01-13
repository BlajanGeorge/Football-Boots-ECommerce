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
    private String firstName;
    @NotBlank(message = "Last name can't be blank.")
    private String lastName;
    @EmailConstraint
    private String email;
    @GenderConstraint
    private String gender;
    @NotNull(message = "Age is required, must be between 14 and 120.")
    @Range(min = 14, max = 120, message = "Age is required, must be between 14 and 120.")
    private Integer age;
    @PasswordConstraint
    private String password;
}
