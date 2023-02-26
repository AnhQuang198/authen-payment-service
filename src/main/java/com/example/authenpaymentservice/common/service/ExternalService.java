package com.example.authenpaymentservice.common.service;

import com.example.authenpaymentservice.common.entity.City;
import com.example.authenpaymentservice.common.entity.District;
import com.example.authenpaymentservice.common.entity.Ward;
import com.example.authenpaymentservice.shop.model.response.ObjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalService extends BaseService {

    public ResponseEntity<?> getCities() {
        List<City> cities = cityRepository.findAll();
        return ResponseEntity.ok(new ObjectResponse<>(cities));
    }

    public ResponseEntity<?> getDistricts(int cityId) {
        List<District> districts = districtRepository.findAllByCityId(cityId);
        return ResponseEntity.ok(new ObjectResponse<>(districts));
    }

    public ResponseEntity<?> getWards(int districtId) {
        List<Ward> wards = wardRepository.findAllByDistrictId(districtId);
        return ResponseEntity.ok(new ObjectResponse<>(wards));
    }
}
