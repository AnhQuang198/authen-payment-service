package com.example.authenpaymentservice.shop.entity;

import com.example.authenpaymentservice.shop.enums.ShopState;
import com.example.authenpaymentservice.shop.utils.PostgreSQLEnumType;
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
@Table(name = "shop", schema = "shops")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class Shop implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    private Integer id;

    @Column(name = "shop_name")
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "state")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private ShopState state;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
