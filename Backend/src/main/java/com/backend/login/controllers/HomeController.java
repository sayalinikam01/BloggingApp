package com.backend.login.controllers;

import com.backend.login.Services.UserService;
import com.backend.login.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HomeController {
    @Autowired
    private UserService userservice;

//    @GetMapping("/user")
//    public List<User> getUser() {
//        return userservice.getUser();
//    }


}
