package org.maperz.scoreboard.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maperz.scoreboard.domain.model.Match;
import org.maperz.scoreboard.infrastructure.InMemoryMatchRepository;

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
    void shouldThrowExceptionWhenStartingSecondMatchWithTeamsReplaced() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Canada", "Mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsNull() {
        assertThrows(NullPointerException.class, () -> {
            scoreboardService.startMatch(null, "Canada");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSame() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Mexico", "Mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Mexico", "mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive2() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("mexico", "Mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive3() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("mexico", "mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive4() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Mexico", "mExico");
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive5() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("mExico", "Mexico");
        });
    }

    @Test
    void shouldUpdateScore() {
        scoreboardService.startMatch("Mexico", "Canada");
        scoreboardService.updateScore("Mexico", "Canada", 1, 0);
        List<Match> matches = scoreboardService.getMatches();
        assertEquals(1, matches.size());
        assertEquals(1, matches.get(0).getScore().getHomeScore());
        assertEquals(0, matches.get(0).getScore().getAwayScore());
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", 1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist2() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", 1, 0);
            scoreboardService.updateScore("Mexico", "Canada", 1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist3() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", 1, 0);
            scoreboardService.updateScore("Canada", "Mexico", 1, 0);
        });
    }


}
