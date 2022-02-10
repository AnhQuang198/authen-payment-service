package com.example.authenpaymentservice.authen.entity;

import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "password_crypt")
    private String password;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private UserState state = UserState.NON_ACTIVE;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
