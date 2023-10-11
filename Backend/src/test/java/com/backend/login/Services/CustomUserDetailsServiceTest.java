package com.backend.login.Services;

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
        //GIVEN
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        //WHEN
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //THEN
        verify(userRepository, times(1)).findByEmail(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void should_throwException_whenUserNotFound() {
        String username = "nonexistent@gmail.com";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );
        assertEquals("User not found", exception.getMessage());
    }
}
