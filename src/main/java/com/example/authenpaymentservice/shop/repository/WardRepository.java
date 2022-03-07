package com.example.authenpaymentservice.shop.repository;

import com.example.authenpaymentservice.shop.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findAllByDistrictId(int districtId);
}
