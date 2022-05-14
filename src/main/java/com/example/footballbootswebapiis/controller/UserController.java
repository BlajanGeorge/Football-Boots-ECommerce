package com.example.footballbootswebapiis.controller;

import com.example.footballbootswebapiis.dto.*;
import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.exceptions.BadCredentialsException;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mappers.UserMapper;
import com.example.footballbootswebapiis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        log.info("Get users request received.");
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        log.info("Get user by id request received for user with id {}.", id);
        Optional<UserResponse> optionalUser = this.userService.getUserById(id);
        return optionalUser.map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable @NotBlank(message = "Email can't be blank") String email) {
        log.info("Get user by email received for user with email {}.", email);
        Optional<UserResponse> optionalUser = this.userService.getUserByEmail(email);
        return optionalUser.map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        log.info("Delete user by id request received for id {}.", id);
        try {
            this.userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("User with id {} deleted.", id);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("Login request received with credentials {}, {}.", userLoginRequest.getEmail(), userLoginRequest.getPassword());
        try {
            return new ResponseEntity(this.userService.login(userLoginRequest), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.warn("Login failed for credentials {}, {}.", userLoginRequest.getEmail(), userLoginRequest.getPassword());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registration/customer")
    public ResponseEntity<UserResponse> createCustomer(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        log.info("Create customer request received with data {}, {}.", userCreateRequest.getEmail(), userCreateRequest.getPassword());
        return new ResponseEntity<>(this.userService.createUser(UserMapper.mapUserCreateRequestToUser(userCreateRequest, Role.CUSTOMER)), HttpStatus.OK);
    }

    @PostMapping("/registration/admin")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        log.info("Create admin request received with data {}, {}.", userCreateRequest.getEmail(), userCreateRequest.getPassword());
        return new ResponseEntity<>(this.userService.createUser(UserMapper.mapUserCreateRequestToUser(userCreateRequest, Role.ADMIN)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUserById(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable @Min(value = 1, message = "id must be positive.") int id) {
        log.info("Update user by id request received for id {}.", id);
        try {
            return new ResponseEntity(this.userService.updateUser(id, userUpdateRequest), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.warn("Update for id {} failed.", id);
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{email}")
    public ResponseEntity sendEmail(@PathVariable String email) {
        log.info("Email sending request was received. Email for {}.", email);
        try {
            this.userService.sendEmailForOrder(email);
        } catch (Exception e) {
            log.warn("Email sending failed.", e);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/order")
    public ResponseEntity order(@RequestBody OrderDto orderDto) {
        log.info("Order request received for email {}, price {}.", orderDto.getEmail(), orderDto.getPrice());
        try {
            this.userService.order(orderDto.getEmail(), BigDecimal.valueOf(orderDto.getPrice()));
            return new ResponseEntity(HttpStatus.OK);
        } catch (HttpStatusCodeException ex) {
            log.error("Order request failed for email {}. Status {}, message {}.", orderDto.getEmail(), ex.getStatusCode(), ex.getMessage());
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        }
    }

    @PostMapping("/logout/user/{email}")
    public ResponseEntity<Object> logout(@PathVariable(value = "email") String email) {
        log.info("Logout request received for email {}.", email);
        this.userService.logout(email);
        log.info("User {} logout with success.", email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<LogHistoryDto>> getLogs() {
        log.info("Logs request received.");
        return new ResponseEntity<>(userService.getLogs(), HttpStatus.OK);
    }

    @GetMapping("/logs/user/{userEmail}")
    public ResponseEntity<List<LogHistoryDto>> getUserLogs(@PathVariable(value = "userEmail") String user) {
        log.info("Logs request received for user {}.", user);
        return new ResponseEntity<>(userService.getUserLogs(user), HttpStatus.OK);
    }

    @GetMapping("/logs/online")
    public ResponseEntity<Integer> getOnlineUsers() {
        log.info("Online users request received.");
        return new ResponseEntity<>(userService.getOnlineUsers(), HttpStatus.OK);
    }
}
