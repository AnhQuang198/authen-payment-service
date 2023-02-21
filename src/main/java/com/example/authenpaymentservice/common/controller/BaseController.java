package com.example.authenpaymentservice.common.controller;

import com.example.authenpaymentservice.common.service.ExternalService;
import com.example.authenpaymentservice.common.service.ExcelProcessService;
import com.example.authenpaymentservice.common.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected StorageService storageService;
    @Autowired
    protected ExcelProcessService excelProcessService;
    @Autowired
    protected ExternalService externalService;
}
