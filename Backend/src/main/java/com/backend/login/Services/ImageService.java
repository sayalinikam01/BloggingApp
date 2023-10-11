package com.backend.login.Services;

import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class ImageService {

    public void deleteImage(String imageurl){

        String IMAGE_DIRECTORY = "C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static/";
        File imageFile = new File(IMAGE_DIRECTORY + imageurl);
        try {
            imageFile.delete();}
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
