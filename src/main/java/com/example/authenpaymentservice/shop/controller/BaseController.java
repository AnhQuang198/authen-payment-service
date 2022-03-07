package com.example.authenpaymentservice.shop.controller;

import com.example.authenpaymentservice.authen.security.jwt.JwtTokenProvider;
import com.example.authenpaymentservice.shop.service.ExternalService;
import com.example.authenpaymentservice.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired protected JwtTokenProvider tokenProvider;
    @Autowired protected ShopService shopService;
    @Autowired protected ExternalService externalService;
}
