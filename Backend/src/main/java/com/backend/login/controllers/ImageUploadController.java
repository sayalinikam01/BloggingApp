package com.backend.login.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("/post/image")
public class ImageUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Path targetPath = Path.of(uploadPath, fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            String imageUrl = "/uploads/" + fileName;
            return new ResponseEntity<>(Map.of("url", imageUrl), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(Map.of("message", "Image upload failed"), HttpStatus.OK);
        }
    }
}