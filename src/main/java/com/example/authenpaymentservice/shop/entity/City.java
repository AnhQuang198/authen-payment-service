package com.example.authenpaymentservice.shop.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    private Integer id;

    @Column(name = "city_name")
    private String cityName;

}
