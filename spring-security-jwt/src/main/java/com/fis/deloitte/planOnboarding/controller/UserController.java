package com.fis.deloitte.planOnboarding.controller;


import com.fis.deloitte.planOnboarding.dto.UserDto;
import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.logout.Blacklist;
import com.fis.deloitte.planOnboarding.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Blacklist blacklist;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = this.userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token=null;
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
        }
        blacklist.blacklistToken(token);
        return  new ResponseEntity<>("You have successfully logged out!!", HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/currentUser")
    public ResponseEntity<String>  getLoggedInUser(Principal principal) {
        return new ResponseEntity<>( principal.getName(), HttpStatus.OK);
    }
}
