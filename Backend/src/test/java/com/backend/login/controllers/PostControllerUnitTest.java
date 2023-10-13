package com.backend.login.controllers;

import com.backend.login.Services.PostService;
import com.backend.login.entities.Post;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerUnitTest {
    @Mock
    PostService postService;

    @Mock
    PostController postController;
    Post post = Post.builder().title("new title")
            .content("new post content that is going to be tested")
            .image("url").build();
    @Test
    void createPost_success(){

        postController.createPost(post);

       verify(postService).createPost(post);


    }

    @Test
    void deletePost(){

      doThrow(EntityNotFoundException.class).when(postController).deletePost("123");
        assertThrows(
                EntityNotFoundException.class,
                () ->    postController.deletePost("123")
        );

    }



}
