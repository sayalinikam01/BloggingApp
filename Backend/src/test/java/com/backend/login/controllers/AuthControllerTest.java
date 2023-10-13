package com.backend.login.controllers;

import com.backend.login.Services.UserService;
import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    AuthController authController;
    @Mock
    private UserService userService;

    @Mock
    UserRepository userRepository;

    User user = new User("id123", "user@gmail.com", "user", "about me", "password");

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void createUser() throws Exception {
        doNothing().when(userService).createUser(user);
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        Mockito.verify(userService, Mockito.times(1)).createUser(any(User.class));


        String responseBody = result.getResponse().getContentAsString();

        boolean res = responseBody.contains("User registered");
        assertTrue(res);

    }

    @Test
    public void testGetLoggedInUser() throws Exception {
        // Mock the logged-in user's email
        String loggedInUserEmail = "user@example.com";

        // Mock the logged-in user
        User loggedInUser = new User();
        loggedInUser.setEmail(loggedInUserEmail);

        when(userRepository.findByEmail(loggedInUserEmail)).thenReturn(Optional.of(loggedInUser));

        try (MockedStatic<SecurityContextHolder> mockedStatic = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("user@gmail.com");
            when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));


            mockMvc.perform(MockMvcRequestBuilders.get("/getuser")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }
    }
}