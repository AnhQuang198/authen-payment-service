package com.example.authenpaymentservice.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public")
public class ExternalController extends BaseController{
    @GetMapping("/cities")
    public ResponseEntity<?> getCities() {
        return externalService.getCities();
    }

    @GetMapping("/districts/{cityId}")
    public ResponseEntity<?> getDistricts(
            @PathVariable("cityId") Integer cityId
    ) {
        return externalService.getDistricts(cityId);
    }

    @GetMapping("/wards/{districtId}")
    public ResponseEntity<?> getWards(
            @PathVariable("districtId") Integer districtId
    ) {
        return externalService.getWards(districtId);
    }
}
