package com.backend.login.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager manager;
    @InjectMocks
    private AuthController authController;


    @Test
    public void testDoAuthenticate_Success() {
        String email = "user@gmail.com";
        String password = "password";
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        when(manager.authenticate(authenticationToken)).thenReturn(authenticationToken);

        authController.doAuthenticate(email, password);

        verify(manager).authenticate(authenticationToken);
    }

    @Test
    public void testDoAuthenticate_BadCredentialsException() {
        String email = "user@gmail.com";
        String password = "incorrectPassword";
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        when(manager.authenticate(authenticationToken)).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> authController.doAuthenticate(email, password));
    }


}
