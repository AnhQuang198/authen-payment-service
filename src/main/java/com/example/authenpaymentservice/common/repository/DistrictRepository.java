package com.example.authenpaymentservice.common.repository;

import com.example.authenpaymentservice.common.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findAllByCityId(int cityId);
}
