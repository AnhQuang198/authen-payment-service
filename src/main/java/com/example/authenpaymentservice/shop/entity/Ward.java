package com.example.authenpaymentservice.shop.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ward")
public class Ward {
    @Id
    private Integer id;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "district_id")
    private Integer districtId;
}
