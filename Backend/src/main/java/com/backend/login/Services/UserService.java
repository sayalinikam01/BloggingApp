package com.backend.login.Services;

import com.backend.login.entities.User;
import com.backend.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder pwdencoder;
    public CustomUserDetailsService cud;

    public List<User> getUser() {
        return userRepo.findAll();
    }

    public User createUser(User user) {
        user.setUserId((UUID.randomUUID().toString()));
        user.setPassword(pwdencoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}