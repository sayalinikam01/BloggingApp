package com.backend.login.security;

import com.backend.login.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtHelperTest {


    @Mock
    private UserDetails userDetails;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtHelper jwtHelper;

    @Test
    void testgenerateToken_Success() {
        User user = new User("id123", "user@gmail.com","user","about me", "password");

        String token = jwtHelper.generateToken(user);
        String username = jwtHelper.getUsernameFromToken(token);

        assertNotNull(token);
        assertEquals("user@gmail.com", username);
    }

    @Test
    void testvalidateToken() {
        User user = new User("id123", "user@gmail.com","user","about me", "password");
        String token = jwtHelper.generateToken(user);

        Boolean result = jwtHelper.validateToken(token, user);
        assertTrue(result);
    }
}