package com.fis.deloitte.planOnboarding.repository;

import com.fis.deloitte.planOnboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    public Optional<User> findByUsername(String email);
}
