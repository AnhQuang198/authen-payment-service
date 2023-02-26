package com.example.authenpaymentservice.common.repository;

import com.example.authenpaymentservice.common.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findAllByDistrictId(int districtId);
}
