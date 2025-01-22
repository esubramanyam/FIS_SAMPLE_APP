package com.fis.deloitte.planOnboarding.service;

import com.fis.deloitte.planOnboarding.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> getUsers();
    public User createUser(User user);
}
