package com.backend.login.Services;

import com.backend.login.entities.Post;
import com.backend.login.entities.User;
import com.backend.login.repositories.PostRepository;
import com.backend.login.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static java.sql.Date.valueOf;

@Service
public class PostService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    public void createPost(Post newpost) {

        newpost.setPostId((UUID.randomUUID().toString()));

        newpost.setImage(newpost.getImage());
        LocalDate today = LocalDate.now();
       // newpost.setCreatedDate(valueOf(today));
       // newpost.setUpdatedDate(valueOf(today));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByEmail(authentication.getName()).get();
        newpost.setUser(LoggedInUser);

        postRepository.save(newpost);
    }

    public void deletePost(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByEmail(authentication.getName()).get();

        User userWhoCreatedPost = post.getUser();
        if (userWhoCreatedPost.equals(LoggedInUser)) {
            postRepository.deleteById(postId);
        } else throw new RuntimeException("Not Authorized");

    }

    public void updatePost(Post updatedPost, String postId) {
        Post initialpost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        initialpost.setTitle(updatedPost.getTitle());
        initialpost.setContent(updatedPost.getContent());
        initialpost.setImage(updatedPost.getImage());
        LocalDate today = LocalDate.now();
       // initialpost.setUpdatedDate(valueOf(today));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByEmail(authentication.getName()).get();

        User userWhoCreatedPost = initialpost.getUser();
        if (userWhoCreatedPost.equals(LoggedInUser)) {
            postRepository.save(initialpost);
        } else throw new RuntimeException("Not Authorized");
    }


}
