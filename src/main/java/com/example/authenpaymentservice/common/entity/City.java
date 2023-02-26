package com.example.authenpaymentservice.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "city", schema = "public")
public class City {
    @Id
    private Integer id;

    @Column(name = "city_name")
    private String cityName;

}
