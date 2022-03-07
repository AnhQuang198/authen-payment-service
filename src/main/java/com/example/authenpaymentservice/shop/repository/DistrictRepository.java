package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findAllByCityId(int cityId);
}
