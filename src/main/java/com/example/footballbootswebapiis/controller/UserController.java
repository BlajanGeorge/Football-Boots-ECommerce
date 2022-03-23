package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.exceptions.BadCredentialsException;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.UserMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Validated
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        Optional<UserResponse> optionalUser = this.userService.getUserById(id);
        return optionalUser.map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable @NotBlank(message = "Email can't be blank") String email) {
        Optional<UserResponse> optionalUser = this.userService.getUserByEmail(email);
        return optionalUser.map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        try {
            this.userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            return new ResponseEntity(this.userService.login(userLoginRequest), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registration/customer")
    public ResponseEntity<UserResponse> createCustomer(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(this.userService.createUser(UserMapper.mapUserCreateRequestToUser(userCreateRequest, Role.CUSTOMER)), HttpStatus.OK);
    }

    @PostMapping("/registration/admin")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(this.userService.createUser(UserMapper.mapUserCreateRequestToUser(userCreateRequest, Role.ADMIN)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserById(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        try {
            return new ResponseEntity(this.userService.updateUser(id, userUpdateRequest), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{email}")
    public ResponseEntity sendEmail(@PathVariable String email) {
        this.userService.sendEmailForOrder(email);
        return new ResponseEntity(HttpStatus.OK);
    }
}
