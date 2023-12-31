package com.backend.login.controllers;

import com.backend.login.Services.ImageService;
import com.backend.login.Services.PostService;
import com.backend.login.Services.SentimentAnalysisService;
import com.backend.login.entities.Post;
import com.backend.login.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    public ImageService imageService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody Post newPost) {

        String sentiment = sentimentAnalysisService.analyzeSentiment(newPost.getContent());

        if ("Negative".equals(sentiment)) {
            return new ResponseEntity<>(Map.of("message", "Negative posts not allowed. Please revise your post."), HttpStatus.BAD_REQUEST);
        } else {
            postService.createPost(newPost);
        }
        logger.info(sentiment);

        return new ResponseEntity<>(Map.of("message", "Post Created"), HttpStatus.OK);
    }


    @GetMapping("/posts")
    public ResponseEntity<?> getPosts() {
        List<Post> allPosts = postRepository.findTop10ByOrderByCreatedDateDesc();
        ;
        if (allPosts.isEmpty()) return new ResponseEntity<>(Map.of("message", "No Posts Found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId) {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>(Map.of("message", "Post Deleted"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Post Not Found", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody Post updatedPost, @PathVariable("postId") String postId) {
        try {
            String sentiment = sentimentAnalysisService.analyzeSentiment(updatedPost.getContent());
            if ("Negative".equals(sentiment)) {
                return new ResponseEntity<>(Map.of("message", "Negative posts not allowed. Please revise your post."), HttpStatus.BAD_REQUEST);
            } else {
                postService.updatePost(updatedPost, postId);
            }
            logger.info(sentiment);
            return new ResponseEntity<>(Map.of("message", "Post Updated"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Map.of("message", "Post Not Found"), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

//    @GetMapping("/posts/{postId}")
//    public void getPostbyId(@PathVariable("postId") String postId) {
//        postRepository.findById(postId);
//    }


}
