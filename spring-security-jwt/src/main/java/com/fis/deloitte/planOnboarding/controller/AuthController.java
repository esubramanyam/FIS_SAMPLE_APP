package com.fis.deloitte.planOnboarding.controller;


import com.fis.deloitte.planOnboarding.dto.JwtReponse;
import com.fis.deloitte.planOnboarding.dto.JwtRequest;
import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.logout.Blacklist;
import com.fis.deloitte.planOnboarding.security.JwtHelper;
import com.fis.deloitte.planOnboarding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;

@RestController
@RequestMapping("/auth")
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User newUser=userService.createUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Invalid Username or password !!");
        }
    }

}
