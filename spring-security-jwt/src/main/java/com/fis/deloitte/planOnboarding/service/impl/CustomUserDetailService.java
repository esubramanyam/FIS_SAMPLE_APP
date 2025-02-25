package com.fis.deloitte.planOnboarding.service.impl;

import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.exception.UserNotFoundException;
import com.fis.deloitte.planOnboarding.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
       //load user from database

            logger.info("Fetching user from username: {}",username);
            try {
                User user = userRepository.findByUsername(username).get();
                logger.info("user found: {}",user.getUsername());
                return user;
            }catch(Exception e){
                logger.error("User not found ");
                throw new UserNotFoundException(e.getMessage());
            }

    }
}
