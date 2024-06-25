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
    void shouldThorwExceptionWhenOneTeamIsAlreadyPlaying() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.startMatch("Mexico", "USA");
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

    @Test
    void shouldThrowExceptionWhenScoreIsNegative() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", -1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative2() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", 0, -1);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative3() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", -1, -1);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative4() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.updateScore("Mexico", "Canada", 0, 0);
        });
    }

    @Test
    void shouldFinishMatch() {
        scoreboardService.startMatch("Mexico", "Canada");
        scoreboardService.finishMatch("Mexico", "Canada");
        List<Match> matches = scoreboardService.getMatches();
        assertEquals(0, matches.size());
    }

    @Test
    void shouldFinishMatch2() {
        scoreboardService.startMatch("Mexico", "Canada");
        scoreboardService.updateScore("Mexico", "Canada", 1, 0);
        scoreboardService.finishMatch("Mexico", "Canada");
        List<Match> matches = scoreboardService.getMatches();
        assertEquals(0, matches.size());
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist4() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.finishMatch("Mexico", "Canada");
        });
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist5() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.finishMatch("Mexico", "Canada");
            scoreboardService.finishMatch("Mexico", "Canada");
        });
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist6() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.finishMatch("Mexico", "Canada");
            scoreboardService.finishMatch("Canada", "Mexico");
        });
    }

    @Test
    void shouldThrowExceptionWhenMatchDoesNotExist7() {
        scoreboardService.startMatch("Mexico", "Canada");
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboardService.finishMatch("Mexico", "Canada");
            scoreboardService.updateScore("Mexico", "Canada", 1, 0);
        });
    }

    @Test
    void shouldGetSummary() {
        scoreboardService.startMatch("Mexico", "Canada");
        scoreboardService.updateScore("Mexico", "Canada", 4, 3);
        scoreboardService.startMatch("USA", "France");
        scoreboardService.updateScore("USA", "France", 2, 3);
        scoreboardService.startMatch("Spain", "Germany");
        scoreboardService.updateScore("Spain", "Germany", 2, 3);
        scoreboardService.startMatch("Denmark", "Sweden");
        scoreboardService.updateScore("Denmark", "Sweden", 2, 3);
        List<Match> summary = scoreboardService.getSummary();
        assertEquals(4, summary.size());
        assertEquals("Mexico", summary.get(0).getHomeTeam().getName());
        assertEquals("USA", summary.get(1).getHomeTeam().getName());
        assertEquals("Spain", summary.get(2).getHomeTeam().getName());
        assertEquals("Denmark", summary.get(3).getHomeTeam().getName());
    }

}
