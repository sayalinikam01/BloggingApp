package com.backend.login.Services;

import com.backend.login.entities.Reaction;
import com.backend.login.entities.User;
import com.backend.login.repositories.ReactionRepository;
import com.backend.login.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReactionService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private ReactionRepository reactionRepository;
    public int addReaction(String postId, int reactionId) {

        User LoggedInUser = userService.getLoggedInUser();

        Reaction react=new Reaction();
        react.setPostId(String.valueOf(postId));
        react.setUserId(String.valueOf(LoggedInUser.getUserId()));
        react.setReactionId(reactionId);
        if(reactionRepository.existsByPostIdAndUserIdAndReactionId(String.valueOf(postId),String.valueOf(LoggedInUser.getUserId()),reactionId))
            throw new RuntimeException("Cannot react multiple times");
        reactionRepository.save(react);
        return reactionRepository.countByPostIdAndReactionId(postId, reactionId);

    }




}
