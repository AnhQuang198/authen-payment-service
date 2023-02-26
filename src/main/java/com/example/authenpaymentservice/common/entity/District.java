package com.example.authenpaymentservice.common.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "district", schema = "public")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "city_id")
    private Integer cityId;
}
