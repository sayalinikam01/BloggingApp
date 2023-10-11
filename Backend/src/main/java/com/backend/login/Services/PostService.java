package com.backend.login.Services;

import com.backend.login.entities.Post;
import com.backend.login.entities.User;
import com.backend.login.repositories.PostRepository;
import com.backend.login.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

import static java.sql.Date.valueOf;

@Service
public class PostService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public ImageService imageService;
    @Autowired
    UserService userService;

    public void createPost(Post newpost) {

        newpost.setPostId((UUID.randomUUID().toString()));
        newpost.setImage(newpost.getImage());
        User LoggedInUser = userService.getLoggedInUser();
        newpost.setUser(LoggedInUser);

        postRepository.save(newpost);
    }

    public void deletePost(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        User LoggedInUser = userService.getLoggedInUser();

        String imageurl = post.getImage();

        User userWhoCreatedPost = post.getUser();
        if (userWhoCreatedPost.equals(LoggedInUser)) {
            postRepository.deleteById(postId);
            imageService.deleteImage(imageurl);
        }
        else throw new RuntimeException("Not Authorized");

    }

    public void updatePost(Post updatedPost, String postId) {
        Post initialpost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        initialpost.setTitle(updatedPost.getTitle());
        initialpost.setContent(updatedPost.getContent());
        initialpost.setImage(updatedPost.getImage());

        User LoggedInUser = userService.getLoggedInUser();


        User userWhoCreatedPost = initialpost.getUser();
        if (userWhoCreatedPost.equals(LoggedInUser)) {
            postRepository.save(initialpost);
        } else throw new RuntimeException("Not Authorized");
    }


}
