package com.fis.deloitte.planOnboarding.service.impl;

import com.fis.deloitte.planOnboarding.dto.UserDto;
import com.fis.deloitte.planOnboarding.entity.User;
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
                        .build();
                userDtos.add(userDto);
            }
            logger.info("Successfully fetched {} users.", userDtos.size());
            return userDtos;
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage(), e);
            throw e;
        }
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
