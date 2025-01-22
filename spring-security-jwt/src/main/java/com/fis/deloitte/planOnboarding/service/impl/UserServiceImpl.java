package com.fis.deloitte.planOnboarding.service.impl;

import com.fis.deloitte.planOnboarding.entity.User;
import com.fis.deloitte.planOnboarding.repository.UserRepository;
import com.fis.deloitte.planOnboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user){
      //  user.setUserId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
