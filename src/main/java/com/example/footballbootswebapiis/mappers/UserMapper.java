package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.model.UserCreateRequest;
import com.example.footballbootswebapiis.model.UserLoginResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User mapUserCreateRequestToUser(UserCreateRequest userCreateRequest, Role role) {
        return new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName(), userCreateRequest.getEmail(), userCreateRequest.getGender()
                , userCreateRequest.getAge(), userCreateRequest.getPassword(), role);
    }

    public static UserLoginResponse mapFromModelToUserLoginResponse(User user)
    {
        return new UserLoginResponse(user.getId(), user.getRole().name(), user.getRole().name().equals("ADMIN"));
    }
}
