package com.example.authenpaymentservice.authen.entity;

import com.example.authenpaymentservice.authen.enums.AuthProvider;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.utils.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user", schema = "users")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class User implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "password_crypt")
    private String password;

    @Column(name = "state")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private UserState state = UserState.NOT_ACTIVE;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "role")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.MEMBER;

    @Column(name = "provider")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "dob")
    private String dob;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
