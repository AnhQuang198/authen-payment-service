package com.example.authenpaymentservice.authen.repository;

import com.example.authenpaymentservice.authen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    User findUserById(int id);
}