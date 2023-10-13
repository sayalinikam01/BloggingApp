package com.backend.login.controllers;

import com.backend.login.Services.ReactionService;
import com.backend.login.Services.UserService;
import com.backend.login.entities.Post;
import com.backend.login.entities.Reaction;
import com.backend.login.entities.User;
import com.backend.login.repositories.ReactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ReactionsControllerTest {


    private MockMvc mockMvc;
    @InjectMocks
    private ReactionsController reactionsController;

    @Mock
    private ReactionService reactionService;

    @Mock
    private ReactionRepository reactionRepository;
    @Mock
    UserService userService;

    Reaction react= new Reaction();
    User user = new User("user123", "user@gmail.com", "user", "about me", "password");


    @BeforeEach
    void setup(){

        react.setPostId("post123");
        react.setUserId("user123");
        react.setReactionId(1);

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reactionsController).build();

    }


    @Test
    void addReaction() throws Exception {
        when(reactionService.addReaction("post123",1)).thenReturn(1);
        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/post/{postId}/reaction/{reactionId}","post123",1))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.count", CoreMatchers.is(1)))
                .andReturn();
        Mockito.verify(reactionService, Mockito.times(1)).addReaction("post123",1);

    }

    @Test
    void totalReaction() throws Exception {
        when(reactionRepository.countByPostIdAndReactionId("123",1)).thenReturn(5);
        when(reactionRepository.countByPostIdAndReactionId("123",2)).thenReturn(3);
        when(reactionRepository.countByPostIdAndReactionId("123",3)).thenReturn(4);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/post/{postId}/totReaction","123"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.likes", CoreMatchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hearts", CoreMatchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celebration", CoreMatchers.is(4)))
                .andReturn();
        Mockito.verify(reactionRepository).countByPostIdAndReactionId("123",1);
        Mockito.verify(reactionRepository).countByPostIdAndReactionId("123",2);
        Mockito.verify(reactionRepository).countByPostIdAndReactionId("123",3);
    }


    @Test
    void shouldReturnTrue_ifReactionPresentforGivenPostUser() throws Exception {
        when(reactionRepository.existsByPostIdAndUserIdAndReactionId("123","user123",1)).thenReturn(true);
        when(reactionRepository.existsByPostIdAndUserIdAndReactionId("123","user123",2)).thenReturn(false);
        when(reactionRepository.existsByPostIdAndUserIdAndReactionId("123","user123",3)).thenReturn(true);

        when(userService.getLoggedInUser()).thenReturn(user);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/post/{postId}/getReaction","123"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.like", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hearts", CoreMatchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.celebration", CoreMatchers.is(true)))
                .andReturn();
        Mockito.verify(reactionRepository).existsByPostIdAndUserIdAndReactionId("123","user123",1);
        Mockito.verify(reactionRepository).existsByPostIdAndUserIdAndReactionId("123","user123",2);
        Mockito.verify(reactionRepository).existsByPostIdAndUserIdAndReactionId("123","user123",3);
    }

}