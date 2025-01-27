package com.fis.deloitte.planOnboarding.controller;


import com.fis.deloitte.planOnboarding.dto.JwtReponse;
import com.fis.deloitte.planOnboarding.dto.JwtRequest;
import com.fis.deloitte.planOnboarding.dto.UserRequest;
import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.exception.UserCreationException;
import com.fis.deloitte.planOnboarding.exception.UserNotFoundException;
import com.fis.deloitte.planOnboarding.logout.Blacklist;
import com.fis.deloitte.planOnboarding.security.JwtHelper;
import com.fis.deloitte.planOnboarding.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    @Autowired
    private Blacklist blacklist;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @CrossOrigin//generate-token
    public ResponseEntity<JwtReponse> login(@RequestBody JwtRequest request){
        try{
        this.doAuthenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token =this.helper.generateToken(userDetails);
        LocalDateTime lastLoginTime = userService.getLastLoginTime(userDetails.getUsername());
        userService.saveLastLogin(request.getUsername());
        JwtReponse response = JwtReponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .lastLoginTime(lastLoginTime)
                .updatedDateTime(lastLoginTime)
                .build();
        logger.info("Login successful for user:{}", request.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Login failed for user:{}.Error:{}",request.getUsername(),e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest user) throws Exception {

        try {
            User newUser = userService.createUser(user);
            logger.info("User created successfully:{}", newUser.getUsername());
            return ResponseEntity.status(201).body(newUser);
        }catch(Exception e){
            logger.error("User creation failed for:{}.Error:{}",user.getUsername(),e.getMessage());
            throw new UserCreationException(e.getMessage());
        }
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authentication);
            logger.info("Authentication successful for user: {}", email);
        }catch(BadCredentialsException e){
            logger.error("Authentication failed for user: {}. Invalid credentials.", email);
            throw new BadCredentialsException("Invalid Username or password !!");
        }
    }

}
