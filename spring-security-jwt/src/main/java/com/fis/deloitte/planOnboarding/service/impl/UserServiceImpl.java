package com.fis.deloitte.planOnboarding.service.impl;

import com.fis.deloitte.planOnboarding.dto.UserDto;
import com.fis.deloitte.planOnboarding.dto.UserRequest;
import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.exception.EmailAlreadyExistsException;
import com.fis.deloitte.planOnboarding.exception.UserException;
import com.fis.deloitte.planOnboarding.exception.UsernameAlreadyExistsException;
import com.fis.deloitte.planOnboarding.repository.UserRepository;
import com.fis.deloitte.planOnboarding.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getUsers() {
        logger.info("Fetching all users...");
        try {
            List<User> users = userRepository.findAll();
            List<UserDto> userDtos = new ArrayList<>();
            for (User user : users) {
                UserDto userDto = UserDto.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .city(user.getCity())
                        .contactNo(user.getContactNo())
                        .lastLoginTime(user.getLastLogin())
                        .updatedDateTime(user.getLastLogin())
                        .build();
                userDtos.add(userDto);
            }
            logger.info("Successfully fetched {} users.", userDtos.size());
            return userDtos;
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage(), e);
            throw new UserException("Error fetching users");
        }
    }

    public User createUser(UserRequest user) throws Exception{
        logger.info("Creating new user: {}", user.getUsername());
        try {
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.error("Username {} already exists", user.getUsername());
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("Email {} already exists", user.getEmail());
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists");
        }
       User newUser= User.builder().username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .contactNo(user.getContactNo())
                .city(user.getCity())
                .build();
        User savedUser = userRepository.save(newUser);
        logger.info("Successfully created user: {}", savedUser.getUsername());
        return savedUser;
    } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
        throw e; // Re-throw the custom exceptions
    } catch (Exception e) {
        logger.error("Error creating user: {}", e.getMessage(), e);
        throw new Exception(e);
    }
}

    @Override
    public LocalDateTime getLastLoginTime(String username) {
        return userRepository.findByUsername(username)
                .map(User::getLastLogin)
                .orElse(null);
    }

    @Override
    public void saveLastLogin(String username){
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }
}
