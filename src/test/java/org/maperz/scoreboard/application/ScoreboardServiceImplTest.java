package org.maperz.scoreboard.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardServiceImplTest {
    private ScoreboardService scoreboardService;

    @BeforeEach
    void setUp() {
        scoreboardService = new ScoreboardServiceImpl(new InMemoryMatchRepository());
    }

    @Test
    void shouldAddMatch() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertEquals(1, scoreboardService.getMatches().size());
    }

    @Test
    void ShouldThrowExceptionWhenMatchAlreadyExists() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Mexico", "Canada");
        });
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("", "Canada");
        });
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsNull() {
        assertThrows(NullPointerException.class, () -> {
            scoreboardService.startMatch(null, "Canada");
        });
    }

}
