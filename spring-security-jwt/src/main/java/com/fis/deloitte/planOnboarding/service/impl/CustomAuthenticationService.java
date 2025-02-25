package com.fis.deloitte.planOnboarding.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationService {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationService(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticate(String username, String password) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
}
