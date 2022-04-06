package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.dto.UserLoginRequest;
import com.example.footballbootswebapiis.dto.UserLoginResponse;
import com.example.footballbootswebapiis.dto.UserResponse;
import com.example.footballbootswebapiis.dto.UserUpdateRequest;
import com.example.footballbootswebapiis.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceApi {

    List<UserResponse> getUsers();

    Optional<UserResponse> getUserById(int id);

    Optional<UserResponse> getUserByEmail(String email);

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserResponse createUser(User user);

    void sendEmailForOrder(String email);

    void deleteUserById(int id);

    UserResponse updateUser(int id, UserUpdateRequest user);


}
