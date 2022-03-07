package com.example.authenpaymentservice.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "district")
public class District {
    @Id
    private Integer id;

    @Column(name = "district_name")
    private String districtName;

    @JoinColumn(name = "city_id")
    private Integer cityId;
}
