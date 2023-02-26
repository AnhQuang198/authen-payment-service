package com.example.authenpaymentservice.common.service;

import com.example.authenpaymentservice.common.repository.CityRepository;
import com.example.authenpaymentservice.common.repository.DistrictRepository;
import com.example.authenpaymentservice.common.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    protected CityRepository cityRepository;
    @Autowired protected DistrictRepository districtRepository;
    @Autowired protected WardRepository wardRepository;
}
