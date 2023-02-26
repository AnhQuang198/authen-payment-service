package com.example.authenpaymentservice.common.service;

import com.example.authenpaymentservice.common.model.Tutorial;
import com.example.authenpaymentservice.common.utils.ExcelHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelProcessService {

    public ResponseEntity<?> uploadExcel(MultipartFile file) {
        List<Tutorial> tutorials = new ArrayList<>();
        try {
            tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
//            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
        return ResponseEntity.ok(tutorials);
    }
}
