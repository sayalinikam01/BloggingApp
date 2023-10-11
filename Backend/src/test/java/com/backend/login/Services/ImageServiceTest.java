package com.backend.login.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Test
    public void testDeleteImage_Success() {
        // Arrange
        String IMAGE_DIRECTORY = "C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static/";
        String imageurl = "testImage.jpg";
        File imageFile = new File(IMAGE_DIRECTORY + imageurl);

        doNothing().when(imageFile).delete();


        imageService.deleteImage(imageurl);

        verify(imageFile, times(1)).delete();
    }

//    @Test
//    public void testDeleteImage_Exception() {
//        // Arrange
//        String IMAGE_DIRECTORY = "C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static/";
//        String imageurl = "testImage.jpg";
//        File imageFile = new File(IMAGE_DIRECTORY + imageurl);
//
//        // Mock the behavior of File.delete() to throw an exception
//        doThrow(new RuntimeException("Unable to delete file")).when(imageFile).delete();
//
//        // Act and Assert
//        try {
//            imageService.deleteImage(imageurl);
//            fail("Expected exception was not thrown");
//        } catch (Exception e) {
//            // Verify that the exception was thrown
//            assertEquals("Unable to delete file", e.getMessage());
//        }
//    }
}
