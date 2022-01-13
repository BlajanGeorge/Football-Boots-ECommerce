package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.customvalidators.EmailValidator;
import com.example.footballbootswebapiis.customvalidators.GenderValidator;
import com.example.footballbootswebapiis.customvalidators.PasswordValidator;
import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.exceptions.BadCredentialsException;
import com.example.footballbootswebapiis.mail.EmailSender;
import com.example.footballbootswebapiis.mappers.UserMapper;
import com.example.footballbootswebapiis.model.User;
import com.example.footballbootswebapiis.model.UserLoginRequest;
import com.example.footballbootswebapiis.model.UserLoginResponse;
import com.example.footballbootswebapiis.model.UserUpdateRequest;
import com.example.footballbootswebapiis.repository.UserRepository;
import com.example.footballbootswebapiis.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return this.userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = this.userRepository.findByEmail(userLoginRequest.getEmail());
        if (userOptional.isPresent()) {
            if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), userOptional.get().getPassword())) {
                return new UserLoginResponse(userOptional.get().getId(), tokenService.getJWTToken(userOptional.get().getFirstName(), userOptional.get().getRole()));
            } else {
                throw new BadCredentialsException("Incorrect email/password!");
            }
        } else {
            throw new BadCredentialsException("Incorrect email/password!");
        }
    }

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            EmailSender.sendEmail(user.getEmail(), "georgeblajan@yahoo.com", "Welcome", user.getFirstName());
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Failed to send the email.");
        }
        return this.userRepository.save(user);
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
        this.userRepository.deleteById(id);
    }

    public User updateUser(int id, UserUpdateRequest user) {
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
        System.out.println("dada");
        if (!StringUtils.isBlank(user.getNewPassword()) && PasswordValidator.isValid(user.getNewPassword())) {
            if (bCryptPasswordEncoder.matches(user.getOldPassword(), oldUser.getPassword())) {
                System.out.println("da");
                oldUser.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
            }
        }
        if (!StringUtils.isBlank(user.getEmail()) && EmailValidator.isValid(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }

        return this.userRepository.save(oldUser);
    }

}
