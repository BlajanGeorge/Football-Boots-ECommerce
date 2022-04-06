package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.dto.UserCreateRequest;
import com.example.footballbootswebapiis.dto.UserLoginResponse;
import com.example.footballbootswebapiis.dto.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User mapUserCreateRequestToUser(UserCreateRequest userCreateRequest, Role role) {
        return new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getGender()
                , userCreateRequest.getAge(), userCreateRequest.getPassword(), role);
    }

    public static UserLoginResponse mapFromModelToUserLoginResponse(User user, String token) {
        return new UserLoginResponse(user.getId(), token, user.getRole().name().equals("ADMIN"));
    }

    public static UserResponse mapFromUserModelToUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getGender(), user.getAge(), user.getRole().name());
    }
}
