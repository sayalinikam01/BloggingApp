package com.backend.login.serviceTest;

import com.backend.login.Services.CustomUserDetailsService;
import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    private CustomUserDetailsService userDetailsService;

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String username = "sayali@gmail.com";
        User user = new User();
        user.setEmail(username);
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        verify(userRepository, times(1)).findByEmail(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistent@gmail.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );
        assertEquals("User not found", exception.getMessage());
    }
}
