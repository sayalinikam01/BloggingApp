package com.backend.login.serviceTest;

import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.backend.login.Services.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {

        User user = new User(null,"newuser@gmail.com","password");

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result.getUserId());
        assertEquals(result.getPassword(),"hashedPassword");

        verify(userRepository,times(1)).save(user);

        assertNotNull(result);
        assertEquals(result,user);
    }

}
