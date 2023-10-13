package com.backend.login.repositories;

import com.backend.login.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    int countByPostIdAndReactionId(String postId, int reactionId);

    boolean existsByPostIdAndUserIdAndReactionId(String postId, String userId, int reactionId);
}
