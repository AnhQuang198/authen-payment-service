package com.example.authenpaymentservice.common.controller;

import com.example.authenpaymentservice.common.service.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected ExternalService externalService;
}
