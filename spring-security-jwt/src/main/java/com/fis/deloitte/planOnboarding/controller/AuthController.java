package com.fis.deloitte.planOnboarding.controller;


import com.fis.deloitte.planOnboarding.dto.JwtReponse;
import com.fis.deloitte.planOnboarding.dto.JwtRequest;
import com.fis.deloitte.planOnboarding.entity.User;
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

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")//generate-token
    public ResponseEntity<JwtReponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token =this.helper.generateToken(userDetails);

        JwtReponse response = JwtReponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User newUser=userService.createUser(user);
        return ResponseEntity.status(201).body(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(email,password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException e){
            throw new RuntimeException("Invalid Username or password !!");
        }
    }

}
