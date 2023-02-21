package com.example.authenpaymentservice.common.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ward", schema = "public")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "district_id")
    private Integer districtId;
}
