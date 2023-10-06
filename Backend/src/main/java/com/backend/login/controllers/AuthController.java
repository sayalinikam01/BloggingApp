package com.backend.login.controllers;

import com.backend.login.Services.CustomUserDetailsService;
import com.backend.login.Services.UserService;
import com.backend.login.entities.User;
import com.backend.login.models.JwtRequest;
import com.backend.login.models.JwtResponse;
import com.backend.login.repositories.UserRepository;
import com.backend.login.security.JwtHelper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping()

public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password  !!");
        }

    }

    @PostMapping("/auth/register")
    public ResponseEntity<?>createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>(Map.of("message", "User registered"), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("User Exists", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getuser")
    public ResponseEntity<?>getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByEmail(authentication.getName()).get();
        Logger log=LoggerFactory.getLogger(AuthController.class);
        log.info(LoggedInUser.toString());
        return new ResponseEntity<>(LoggedInUser, HttpStatus.OK);
    }


}
