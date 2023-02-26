package com.example.authenpaymentservice.common.repository;

import com.example.authenpaymentservice.common.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}
