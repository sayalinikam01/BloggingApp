package com.backend.login.Services;

import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    User user = new User("id123", "user@gmail.com", "user", "about me", "password");
//    @Captor
//    private ArgumentCaptor<User> userCaptor;
    @Test
    public void should_createUser_whenValidDetailsGiven() {

//        //User user = new User(null, "newuser@gmail.com", "password");
//
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
//
        userService.createUser(user);
//
//       // assertNotNull(result.getUserId());
//       // assertEquals(result.getPassword(), "hashedPassword");
//
//        verify(userRepository, times(1)).save(user);
//
//        //assertNotNull(result);
//        //assertEquals(result, user);
        }



    @Test
    public void should_returnLoggedInUser_usingSecurityContext() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = Mockito.mockStatic(SecurityContextHolder.class)) {
            // GIVEN
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("user@gmail.com");
            when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));

            //WHEN
            User result = userService.getLoggedInUser();

            //THEN
            verify(userRepository, times(1)).findByEmail("user@gmail.com");
            assertEquals(user, result);

        }

    }

}
