package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.customvalidators.EmailValidator;
import com.example.footballbootswebapiis.customvalidators.GenderValidator;
import com.example.footballbootswebapiis.customvalidators.PasswordValidator;
import com.example.footballbootswebapiis.exceptions.BadCredentialsException;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mail.EmailSender;
import com.example.footballbootswebapiis.mappers.UserMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.repository.UserRepository;
import com.example.footballbootswebapiis.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final BasketService basketService;

    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder bCryptPasswordEncoder, final TokenService tokenService, final BasketService basketService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.basketService = basketService;
    }

    public List<UserResponse> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : this.userRepository.findAll()) {
            userResponses.add(UserMapper.mapFromUserModelToUserResponse(user));
        }
        return userResponses;
    }

    public Optional<UserResponse> getUserById(int id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.map(UserMapper::mapFromUserModelToUserResponse);
    }

    public Optional<UserResponse> getUserByEmail(String email) {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        return userOptional.map(UserMapper::mapFromUserModelToUserResponse);
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = this.userRepository.findByEmail(userLoginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
                return UserMapper.mapFromModelToUserLoginResponse(user, tokenService.getJWTToken(user.getFirstName(), user.getRole()));
            } else {
                throw new BadCredentialsException("Incorrect email/password!");
            }
        } else {
            throw new BadCredentialsException("Incorrect email/password!");
        }
    }

    public UserResponse createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            EmailSender.sendEmail(user.getEmail(), "georgeblajan@yahoo.com", "Welcome", user.getFirstName());
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Failed to send the email.");
        }
        return UserMapper.mapFromUserModelToUserResponse(this.userRepository.save(user));
    }

    public void sendEmailForOrder(String email) {
        try {
            EmailSender.sendEmailForOrder("Order", "georgeblajan@yahoo.com", email);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Failed to send the email.");
        }
    }

    public void deleteUserById(int id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist.", id)));
        List<Basket> baskets = this.basketService.getAll();
        for (Basket basket : baskets) {
            if (basket.getIdUser() == id) {
                this.basketService.deleteEntryById(basket.getIdBasket());
            }
        }
        this.userRepository.deleteById(id);
    }

    public UserResponse updateUser(int id, UserUpdateRequest user) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        User oldUser = optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist.", id)));

        if (!StringUtils.isBlank(user.getFirstName())) {
            oldUser.setFirstName(user.getFirstName());
        }
        if (!StringUtils.isBlank(user.getLastName())) {
            oldUser.setLastName(user.getLastName());
        }
        if (!StringUtils.isBlank(user.getGender()) && GenderValidator.isValid(user.getGender())) {
            oldUser.setGender(user.getGender());
        }
        if (user.getAge() != null && user.getAge() >= 14 && user.getAge() <= 120) {
            oldUser.setAge(user.getAge());
        }
        if (!StringUtils.isBlank(user.getNewPassword()) && PasswordValidator.isValid(user.getNewPassword())) {
            if (bCryptPasswordEncoder.matches(user.getOldPassword(), oldUser.getPassword())) {
                oldUser.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
            }
        }
        if (!StringUtils.isBlank(user.getEmail()) && EmailValidator.isValid(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }

        return UserMapper.mapFromUserModelToUserResponse(this.userRepository.save(oldUser));
    }

}
