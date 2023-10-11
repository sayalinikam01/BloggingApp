package com.backend.login.Services;

import com.backend.login.entities.Reaction;
import com.backend.login.entities.User;
import com.backend.login.repositories.ReactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactionServiceTest {

    @Mock
    UserService userService;
    @Mock
    ReactionRepository reactionRepository;
    @Captor
    private ArgumentCaptor<Reaction> reactionCaptor;
    @InjectMocks
    private ReactionService reactionService;
    User user = new User("id123", "user@gmail.com", "user", "about me", "password");
    Reaction react = new Reaction("post123", "id123", 1);

    @Test
    void addReaction_Success() {
        //GIVEN
        when(userService.getLoggedInUser()).thenReturn(user);
        when(reactionRepository.existsByPostIdAndUserIdAndReactionId("post123", "id123", 1))
                .thenReturn(false);
        //WHEN
        reactionService.addReaction("post123", 1);

        //THEN
        verify(reactionRepository).save(reactionCaptor.capture());
        Reaction savedReaction = reactionCaptor.getValue();
        assertEquals("post123", savedReaction.getPostId());
        assertEquals(user.getUserId(), savedReaction.getUserId());
        assertEquals(1, savedReaction.getReactionId());
    }

    @Test
    public void addReaction_Fail_DuplicateReaction() {
        when(userService.getLoggedInUser()).thenReturn(user);
        when(reactionRepository.existsByPostIdAndUserIdAndReactionId("post123", "id123", 1))
                .thenReturn(true);


        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reactionService.addReaction("post123", 1)
        );
        assertEquals("Cannot react multiple times", exception.getMessage());

    }
}