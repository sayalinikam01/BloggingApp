package com.backend.login.Services;

import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder pwdencoder;
    public CustomUserDetailsService cud;
    @Autowired
    private  UserRepository userRepository;

//   // public List<User> getUser() {
//        return userRepo.findAll();
//    }

    Logger log= LoggerFactory.getLogger(UserService.class);

    public void createUser(User user) {
        log.info(user.toString());
        user.setUserId((UUID.randomUUID().toString()));
        user.setPassword(pwdencoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).get();
    }
}