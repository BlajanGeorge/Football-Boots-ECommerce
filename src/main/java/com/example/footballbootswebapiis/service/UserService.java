package com.example.footballbootswebapiis.service;

import com.example.footballbootswebapiis.customvalidators.EmailValidator;
import com.example.footballbootswebapiis.customvalidators.GenderValidator;
import com.example.footballbootswebapiis.customvalidators.PasswordValidator;
import com.example.footballbootswebapiis.dto.*;
import com.example.footballbootswebapiis.exceptions.BadCredentialsException;
import com.example.footballbootswebapiis.exceptions.EntityNotFoundException;
import com.example.footballbootswebapiis.mail.Email;
import com.example.footballbootswebapiis.mail.EmailSender;
import com.example.footballbootswebapiis.mappers.UserMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.repository.BasketRepository;
import com.example.footballbootswebapiis.repository.FavoritesRepository;
import com.example.footballbootswebapiis.repository.LogHistoryRepository;
import com.example.footballbootswebapiis.repository.UserRepository;
import com.example.footballbootswebapiis.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.footballbootswebapiis.constants.Constants.FROM_EMAIL;

@Service
@Slf4j
public class UserService implements UserServiceApi {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenService tokenService;
    private final BasketRepository basketRepository;
    private final FavoritesRepository favoritesRepository;
    private final LogHistoryRepository logHistoryRepository;
    private final RestTemplate restTemplate;

    public UserService(final UserRepository userRepository,
                       final BCryptPasswordEncoder bCryptPasswordEncoder,
                       final TokenService tokenService,
                       final BasketRepository basketRepository,
                       final FavoritesRepository favoritesRepository,
                       final LogHistoryRepository logHistoryRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
        this.basketRepository = basketRepository;
        this.favoritesRepository = favoritesRepository;
        this.logHistoryRepository = logHistoryRepository;
        this.restTemplate = new RestTemplate();
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
                logHistoryRepository.save(new LogHistory(user.getEmail(), "LOGIN"));
                return UserMapper.mapFromModelToUserLoginResponse(user, tokenService.getJWTToken(user.getFirstName(), user.getRole()));
            } else {
                throw new BadCredentialsException("Incorrect email/password!");
            }
        } else {
            throw new BadCredentialsException("Incorrect email/password!");
        }
    }

    public void logout(final String email) {
        this.logHistoryRepository.save(new LogHistory(email, "LOGOUT"));
    }

    @Transactional
    public UserResponse createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            Email email = new Email.EmailBuilder(user.getEmail(), FROM_EMAIL)
                    .body(String.format("Hi %s, we are glad to have you on footballboots.com, be sure you keep an eye on our offers!", user.getFirstName()))
                    .subject("Welcome")
                    .build();

            EmailSender.sendEmail(email);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            log.error("Failed to send the email.");
        }
        return UserMapper.mapFromUserModelToUserResponse(this.userRepository.save(user));
    }

    public void sendEmailForOrder(String email) {
        try {
            Email emailEntity = new Email.EmailBuilder(email, FROM_EMAIL)
                    .body("\"Order was processed, thank you!\"")
                    .subject("Order")
                    .build();

            EmailSender.sendEmail(emailEntity);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            log.error("Failed to send the email.");
        }
    }

    public void order(final String email, final BigDecimal price) {
        restTemplate.exchange("http://localhost:10002/account", HttpMethod.PUT, new HttpEntity<>(new UpdateAccountDto(email, price), null), Object.class);
    }

    @Transactional
    public void deleteUserById(int id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist.", id)));
        List<Basket> baskets = this.basketRepository.findAll();
        for (Basket basket : baskets) {
            if (basket.getIdUser() == id) {
                this.basketRepository.deleteByIdBasket(basket.getIdBasket());
            }
        }
        this.favoritesRepository.deleteAllByUserId(id);
        this.userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse updateUser(int id, UserUpdateRequest user) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        User oldUser = optionalUser.orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d doesn't exist.", id)));

        if (!StringUtils.isBlank(user.getEmail())) {
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
        if (!StringUtils.isBlank(user.getNewPassword()) && PasswordValidator.isValid(user.getNewPassword()) && bCryptPasswordEncoder.matches(user.getOldPassword(), oldUser.getPassword())) {
            oldUser.setPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));
        }
        if (!StringUtils.isBlank(user.getEmail()) && EmailValidator.isValid(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }

        return UserMapper.mapFromUserModelToUserResponse(this.userRepository.save(oldUser));
    }

    public List<LogHistoryDto> getLogs() {
        List<LogHistory> logHistories = this.logHistoryRepository.findAll();
        List<LogHistoryDto> logHistoryDtos = new ArrayList<>();
        for (LogHistory logHistory : logHistories) {
            logHistoryDtos.add(new LogHistoryDto(logHistory.getUser(), logHistory.getOperation(), logHistory.getTimestamp()));
        }

        return logHistoryDtos;
    }

    public List<LogHistoryDto> getUserLogs(final String user) {
        List<LogHistory> logHistories = this.logHistoryRepository.findAll();
        List<LogHistoryDto> logHistoryDtos = new ArrayList<>();
        for (LogHistory logHistory : logHistories) {
            if (logHistory.getUser().contains(user)) {
                logHistoryDtos.add(new LogHistoryDto(logHistory.getUser(), logHistory.getOperation(), logHistory.getTimestamp()));
            }
        }
        return logHistoryDtos;
    }

    public Integer getOnlineUsers() {
        int count = 0;
        List<User> users = userRepository.findAll();
        for (User user : users) {
            LogHistory logHistory = logHistoryRepository.getLogHistoryByUserAndMaxTimestamp(user.getEmail());
            if (logHistory != null && logHistory.getOperation().equals("LOGIN") && logHistory.getTimestamp().getTime() > Instant.now().toEpochMilli() - 1200000) {
                count++;
            }
        }

        return count;
    }

}
