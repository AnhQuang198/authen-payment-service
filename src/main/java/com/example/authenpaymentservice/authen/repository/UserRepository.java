package com.example.authenpaymentservice.authen.repository;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.dtos.UserStatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
    User findUserById(int id);

    @Query("SELECT new com.example.authenpaymentservice.authen.dtos.UserStatusDTO(u.id, u.isLocked, u.state) FROM User u WHERE u.id = ?1")
    UserStatusDTO getUserState(int userId);
}