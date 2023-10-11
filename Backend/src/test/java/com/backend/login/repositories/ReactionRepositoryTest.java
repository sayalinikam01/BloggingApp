package com.backend.login.repositories;

import com.backend.login.entities.Reaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class ReactionRepositoryTest {

    @Mock
    Reaction reaction;

    @Autowired
    ReactionRepository reactionRepository;

    @Test
    void countByPostIdAndReactionId() {

        Reaction react1 = new Reaction(123, "23165722-144f-4947-8ebb-4f14b73bb981", "8597d7cd-c961-4446-99e9-41f348d5c230", 1);
        Reaction react2 = new Reaction(123, "5115fdb5-4272-4049-9e76-24606ec34e21", "8597d7cd-c961-4446-99e9-41f348d5c230", 2);
        Reaction react3 = new Reaction(123, "23165722-144f-4947-8ebb-4f14b73bb981", "8597d7cd-c961-4446-99e9-41f348d5c230", 1);

        reactionRepository.save(react1);
        reactionRepository.save(react2);
        reactionRepository.save(react3);

        int totalReactionsOfPost = reactionRepository.countByPostIdAndReactionId("23165722-144f-4947-8ebb-4f14b73bb981", 1);
        assertEquals(2, totalReactionsOfPost);

    }

    @Test
    void existsByPostIdAndUserIdAndReactionId_ReactionExists() {
        Reaction react1 = new Reaction(123, "23165722-144f-4947-8ebb-4f14b73bb981", "8597d7cd-c961-4446-99e9-41f348d5c230", 1);
        reactionRepository.save(react1);
        boolean reactionforPostbyUser = reactionRepository.existsByPostIdAndUserIdAndReactionId("23165722-144f-4947-8ebb-4f14b73bb981", "8597d7cd-c961-4446-99e9-41f348d5c230", 1);
        assertTrue(reactionforPostbyUser);
    }

    @Test
    void existsByPostIdAndUserIdAndReactionId_ReactionDoesNotExist() {
        boolean reactionforPostbyUser = reactionRepository.existsByPostIdAndUserIdAndReactionId("23165722-144f-4947-8ebb-4f14b73bb981", "8597d7cd-c961-4446-99e9-41f348d5c230", 1);
        assertFalse(reactionforPostbyUser);
    }
}