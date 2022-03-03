package com.example.authenpaymentservice.upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.authenpaymentservice.exception.Message;
import com.example.authenpaymentservice.exception.ResourceNotFoundException;
import com.example.authenpaymentservice.upload.model.UploadResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@Log4j2
public class StorageService {

    @Value("${amazonS3.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonS3.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public ResponseEntity<?> uploadImage(MultipartFile fileUpload) {
        UploadResponse response = new UploadResponse();
        try {
            String fileName = uploadToAmazonS3(fileUpload);
            String filePath = String.format("%s/%s", endpointUrl, fileName);
            response.setFilePath(filePath);
            response.setStatusCode(HttpStatus.OK.value());
        } catch (Exception ex) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.error("Upload failed: {}", ex);
            throw new ResourceNotFoundException(ex.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)){
            fos.write(file.getBytes());
        } catch (IOException ex) {
            log.error("Invalid access file: {}", ex);
        }
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        String fileName = multiPart.getOriginalFilename();
        Optional<String> fileExtension = getExtensionFile(fileName);
        if (fileExtension.isPresent()) {
            ArrayList<String> exts = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));
            if (!exts.contains(fileExtension.get())) {
                throw new ResourceNotFoundException(Message.FILE_EXTENSION_INVALID);
            }
        } else {
            throw new ResourceNotFoundException(Message.FILE_EXTENSION_INVALID);
        }
        return String.format("%s.%s", System.currentTimeMillis(), fileExtension.get());
    }

    private String uploadToAmazonS3(MultipartFile file) {
        File fileUpload = convertMultiPartToFile(file);
        String fileName = generateFileName(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileUpload));
        fileUpload.delete();
        return fileName;
    }

    private Optional<String> getExtensionFile(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
