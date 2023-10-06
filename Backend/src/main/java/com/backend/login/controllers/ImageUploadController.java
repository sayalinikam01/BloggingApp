package com.backend.login.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;



@RestController
@RequestMapping("/api/images")
public class ImageUploadController {



    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        Logger logger= LoggerFactory.getLogger(ImageUploadController.class);
        try {
            // Generate a unique filename for the uploaded image
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();


            logger.info("he");
            // Save the file to the specified upload directory
            Path targetPath = Path.of(uploadPath, fileName);
            logger.info("aupload");
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            logger.info("copy");

            // Return the URL of the uploaded image
            String imageUrl = "/uploads/" + fileName;
           // String imageUrl ="C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static//Images//"+fileName;



            // This assumes your images will be served from '/uploads/'
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image");
        }
    }
}