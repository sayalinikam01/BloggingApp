package com.backend.login.Services;

import com.backend.login.entities.Post;
import com.backend.login.entities.User;
import com.backend.login.repositories.PostRepository;
import com.backend.login.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @InjectMocks
    PostService postService;

    @Mock
    ImageService imageService;


    User user = new User("id123", "user@gmail.com", "user", "about me", "password");
    User unauthorizedUser=new User("id456", "unathUser@gmail.com", "unathUser", "about me", "password");

    Post postForTesting = Post.builder().postId("abc123").title("new title")
            .content("new post content that is going to be tested")
            .image("url").createdDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .updatedDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .user(user).build();

    @Test
    void should_createPost_whenValidDetailsGiven() {

        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(user);

        //WHEN
        postService.createPost(postForTesting);

        //THEN
        verify(postRepository).save(postForTesting);
    }

    @Test
    void should_deletePost_whenValidDetailsGiven() {

        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(user);
        when(postRepository.findById("abc123")).thenReturn(Optional.of(postForTesting));

        // WHEN
        postService.deletePost("abc123");

        // THEN
        verify(postRepository).deleteById("abc123");
        verify(imageService).deleteImage(postForTesting.getImage());

    }

    @Test
    void should_returnException_whenUnauthorizedUserTriesToDelete() {
        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(unauthorizedUser);
        when(postRepository.findById("abc123")).thenReturn(Optional.of(postForTesting));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () ->   postService.deletePost("abc123")
        );
        assertEquals("Not Authorized", exception.getMessage());

    }



    @Test
    void should_updatePost_whenValidDetailsGiven() {
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");
        updatedPost.setImage("Updated Image");

        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(user);
        when(postRepository.findById("abc123")).thenReturn(Optional.of(postForTesting));

        // WHEN
        postService.updatePost(updatedPost, "abc123");

        // THEN
        verify(postRepository).save(postForTesting);

    }


    @Test
    void should_returnException_whenUnauthorizedUserTriesToUpdate(){
        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");
        updatedPost.setImage("Updated Image");

        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(unauthorizedUser);
        when(postRepository.findById("abc123")).thenReturn(Optional.of(postForTesting));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () ->   postService.updatePost(updatedPost,"abc123")
        );
        assertEquals("Not Authorized", exception.getMessage());


    }
}