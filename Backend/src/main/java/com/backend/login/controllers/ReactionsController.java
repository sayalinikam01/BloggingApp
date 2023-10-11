package com.backend.login.controllers;

import com.backend.login.Services.ReactionService;
import com.backend.login.entities.Post;
import com.backend.login.entities.User;
import com.backend.login.repositories.ReactionRepository;
import com.backend.login.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins="*")
public class ReactionsController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{postId}/reaction/{reactionId}")
    public ResponseEntity<?> addReaction(@PathVariable("postId") String postId,@PathVariable("reactionId") int reactionId) {
        try{
            int totalReactions=reactionService.addReaction(postId,reactionId);
            return new ResponseEntity<>(Map.of("count", totalReactions,"message", "Reaction added"), HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{postId}/totReaction")
    public ResponseEntity<?> totReaction(@PathVariable("postId") String postId) {
        int totalLikes=reactionRepository.countByPostIdAndReactionId(postId,1);
        int totalHearts=reactionRepository.countByPostIdAndReactionId(postId,2);
        int totalCelebration=reactionRepository.countByPostIdAndReactionId(postId,3);
        return new ResponseEntity<>(Map.of("likes", totalLikes,"hearts",totalHearts,"celebration",totalCelebration), HttpStatus.OK);

    }

    @GetMapping("/{postId}/getReaction")
    public ResponseEntity<?> IsReaction(@PathVariable("postId") String postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User LoggedInUser = userRepository.findByEmail(authentication.getName()).get();

        boolean isLiked=reactionRepository.existsByPostIdAndUserIdAndReactionId(String.valueOf(postId),String.valueOf(LoggedInUser.getUserId()),1);
        boolean isHeart=reactionRepository.existsByPostIdAndUserIdAndReactionId(String.valueOf(postId),String.valueOf(LoggedInUser.getUserId()),2);
        boolean isCelebrate=reactionRepository.existsByPostIdAndUserIdAndReactionId(String.valueOf(postId),String.valueOf(LoggedInUser.getUserId()),3);

        return new ResponseEntity<>(Map.of("like", isLiked,"hearts",isHeart,"celebration",isCelebrate), HttpStatus.OK);

    }

}
