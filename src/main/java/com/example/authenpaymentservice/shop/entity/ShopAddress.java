package com.example.authenpaymentservice.shop.entity;

import com.example.authenpaymentservice.shop.enums.ShopAddressType;
import com.example.authenpaymentservice.shop.utils.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shop_address", schema = "shops")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class ShopAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "ward_id")
    private Integer wardId;

    @Column(name = "add_detail")
    private String addDetail;

    @Column(name = "type")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private ShopAddressType type;

    @Column(name = "is_pickup")
    private Boolean isPickup;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
