package com.fis.deloitte.planOnboarding.service;

import com.fis.deloitte.planOnboarding.dto.UserDto;
import com.fis.deloitte.planOnboarding.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface UserService {

    public List<UserDto> getUsers();
    public User createUser(User user);
    public LocalDateTime getLastLoginTime(String username) ;

    void saveLastLogin(String username);
}
