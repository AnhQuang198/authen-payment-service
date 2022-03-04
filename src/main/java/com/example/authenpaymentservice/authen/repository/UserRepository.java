package com.example.authenpaymentservice.authen.repository;

import com.example.authenpaymentservice.authen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    User findUserById(int id);

    @Query("SELECT u.id, u.isLocked, u.state FROM User u WHERE u.id = ?1")
    User getUserState(int userId);
}