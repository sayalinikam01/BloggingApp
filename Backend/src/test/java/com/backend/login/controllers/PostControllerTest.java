package com.backend.login.controllers;

import com.backend.login.Services.PostService;
import com.backend.login.entities.Post;
import com.backend.login.entities.User;
import com.backend.login.repositories.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;
@Mock
    private PostRepository postRepository;

    User user = new User("id123", "user@gmail.com", "user", "about me", "password");

    Post postForTesting = Post.builder().title("new title")
            .content("new post content that is going to be tested")
            .image("url").createdDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .updatedDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .user(user).build();
    Post postForTesting2 = Post.builder().title("new title2")
            .content("new post2 content that is going to be tested")
            .image("url2").createdDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .updatedDate(Timestamp.valueOf("2023-10-10 20:20:20"))
            .user(user).build();
    Post post = Post.builder().title("new title")
            .content("new post content that is going to be tested")
            .image("url").build();

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void createPost() throws Exception {

        doNothing().when(postService).createPost(post);
        ObjectMapper objectMapper = new ObjectMapper();


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        Mockito.verify(postService, Mockito.times(1)).createPost(any(Post.class));


        String responseBody = result.getResponse().getContentAsString();

        boolean res = responseBody.contains("Post Created");
        assertTrue(res);

        Mockito.verify(postService, Mockito.times(1)).createPost(any(Post.class));
    }

    @Test
    public void getPosts_ReturnsListOfPosts() throws Exception {
        // Create a list of sample posts for testing
        List<Post> samplePosts = Arrays.asList(
                postForTesting,
                postForTesting2
        );

        when(postRepository.findTop10ByOrderByCreatedDateDesc()).thenReturn(samplePosts);

        ObjectMapper objectMapper= new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)))
                .andReturn();

//    }
    }

    @Test
    public void deletePost() throws Exception {

        doNothing().when(postService).deletePost("123");
        ObjectMapper objectMapper = new ObjectMapper();


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/{postId}","123"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Mockito.verify(postService, Mockito.times(1)).deletePost("123");
        String responseBody = result.getResponse().getContentAsString();

        boolean res = responseBody.contains("Post Deleted");
        assertTrue(res);

    }

}
