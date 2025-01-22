package com.fis.deloitte.planOnboarding.controller;


import com.fis.deloitte.planOnboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/currentUser")
    public String getLoggedInUser(Principal principal) {
        return principal.getName();
    }
    //post api for user registration
    //planOnboardingService
    //com.fis.deloitte.planonboarding
    //customerException
    //http request & responses
    //validations needs to be added
    //Create rest api for user registration. We can use swagger/post man for registering the user.
    //api : that shows last logged in for user
    //
}
