package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.model.UserCreateRequest;
import com.example.footballbootswebapiis.model.UserLoginResponse;

public class UserMapper {

    public static User mapUserCreateRequestToUser(UserCreateRequest userCreateRequest, Role role) {
        return new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getGender()
                , userCreateRequest.getAge(), userCreateRequest.getPassword(), role);
    }

    public static UserLoginResponse mapFromModelToUserLoginRequest(User user)
    {
        return new UserLoginResponse(user.getId(), user.getRole().name());
    }
}
