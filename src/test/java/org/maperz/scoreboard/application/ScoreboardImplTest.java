package org.maperz.scoreboard.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maperz.scoreboard.application.factory.ScoreboardFactory;
import org.maperz.scoreboard.domain.exception.InvalidScoreException;
import org.maperz.scoreboard.domain.exception.InvalidTeamNameException;
import org.maperz.scoreboard.domain.exception.GameNotFoundException;
import org.maperz.scoreboard.domain.model.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardImplTest {
    private Scoreboard scoreboard;

    private static final String HOME_TEAM_NAME = "Mexico";
    private static final String AWAY_TEAM_NAME = "Canada";

    private UUID generateGenericGameExceptionHandled() {
        try {
            return scoreboard.startGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        } catch (InvalidTeamNameException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateScoreExceptionHandled(final UUID id, final int homeScore, final int awayScore) {
        try {
            scoreboard.updateScore(id, homeScore, awayScore);
        } catch (GameNotFoundException | InvalidScoreException e) {
            e.printStackTrace();
        }
    }

    private void finishGameExceptionHandled(final UUID id) {
        try {
            scoreboard.finishGame(id);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        scoreboard = ScoreboardFactory.createScoreboard();
    }

    @Test
    void shouldAddGame() {

        generateGenericGameExceptionHandled();

        List<Game> games = scoreboard.getGames();
        assertEquals(1, games.size());
        assertEquals(HOME_TEAM_NAME, games.get(0).homeTeam().name());
        assertEquals(AWAY_TEAM_NAME, games.get(0).awayTeam().name());
    }

    @Test
    void ShouldThrowExceptionWhenGameAlreadyExists() {

        generateGenericGameExceptionHandled();

        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        });
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsEmpty() {
        final String homeTeamName = "";

        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(homeTeamName, AWAY_TEAM_NAME);
        });
    }

    @Test
    void shouldThrowExceptionWhenStartingSecondGameWithTeamsReplaced() {

        generateGenericGameExceptionHandled();

        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(AWAY_TEAM_NAME, HOME_TEAM_NAME);
        });
    }

    @Test
    void shouldThorwExceptionWhenOneTeamIsAlreadyPlaying() {
        final String anotherTeamName = "USA";

        generateGenericGameExceptionHandled();

        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(anotherTeamName, AWAY_TEAM_NAME);
        });
    }

    @Test
    void shouldThrowExceptionWhenTeamNameIsNull() {
        final String homeTeamName = null;

        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(homeTeamName, AWAY_TEAM_NAME);
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSame() {
        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(HOME_TEAM_NAME, HOME_TEAM_NAME);
        });
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamAndAwayTeamAreTheSameCaseInsensitive() {
        assertThrows(InvalidTeamNameException.class, () -> {
            scoreboard.startGame(HOME_TEAM_NAME, HOME_TEAM_NAME.toLowerCase());
        });
    }

    @Test
    void shouldUpdateScore() {

        final UUID id = generateGenericGameExceptionHandled();

        updateScoreExceptionHandled(id, 1, 0);

        List<Game> games = scoreboard.getGames();
        assertEquals(1, games.size());
        assertEquals(1, games.get(0).score().homeScore());
        assertEquals(0, games.get(0).score().awayScore());
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist() {
        final UUID id = UUID.randomUUID();

        assertThrows(GameNotFoundException.class, () -> {
            scoreboard.updateScore(id, 1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist2() {
        final UUID randomUUID = UUID.randomUUID();
        generateGenericGameExceptionHandled();

        assertThrows(GameNotFoundException.class, () -> {
            scoreboard.updateScore(randomUUID, 1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(InvalidScoreException.class, () -> {
            scoreboard.updateScore(id, -1, 0);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative2() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(InvalidScoreException.class, () -> {
            scoreboard.updateScore(id, 0, -1);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative3() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(InvalidScoreException.class, () -> {
            scoreboard.updateScore(id, -1, -1);
        });
    }

    @Test
    void shouldThrowExceptionWhenScoreIsNegative4() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(InvalidScoreException.class, () -> {
            scoreboard.updateScore(id, 0, 0);
        });
    }

    @Test
    void shouldFinishGame() {
        final UUID id = generateGenericGameExceptionHandled();

        finishGameExceptionHandled(id);

        List<Game> games = scoreboard.getGames();
        assertEquals(0, games.size());
    }

    @Test
    void shouldFinishGame2() {
        final UUID id = generateGenericGameExceptionHandled();
        updateScoreExceptionHandled(id, 1, 0);

        finishGameExceptionHandled(id);

        List<Game> games = scoreboard.getGames();
        assertEquals(0, games.size());
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist4() {
        assertThrows(GameNotFoundException.class, () -> {
            scoreboard.finishGame(UUID.randomUUID());
        });
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist5() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(GameNotFoundException.class, () -> {
            scoreboard.finishGame(id);
            scoreboard.finishGame(id);
        });
    }

    @Test
    void shouldThrowExceptionWhenGameDoesNotExist6() {
        final UUID id = generateGenericGameExceptionHandled();

        assertThrows(GameNotFoundException.class, () -> {
            scoreboard.finishGame(id);
            scoreboard.updateScore(id, 1, 0);
        });
    }
    @Test
    void shouldReturnSummaryInOrder() {
        final List<String> teamNames = List.of("Mexico", "Canada", "USA", "Brazil", "Argentina", "Germany");
        final List<LocalDateTime> startTimes = List.of(
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 1, 11, 0),
                LocalDateTime.of(2024, 1, 1, 12, 0)
        );

        try {
            final UUID id1 = scoreboard.startGame(teamNames.get(0), teamNames.get(1), startTimes.get(0));
            final UUID id2 = scoreboard.startGame(teamNames.get(2), teamNames.get(3), startTimes.get(1));
            final UUID id3 = scoreboard.startGame(teamNames.get(4), teamNames.get(5), startTimes.get(2));

            scoreboard.updateScore(id1, 1, 0);
            scoreboard.updateScore(id2, 2, 3);
            scoreboard.updateScore(id3, 3, 2);

            final List<Game> summary = scoreboard.getSummary();
            assertEquals(3, summary.size());
            assertEquals(id3, summary.get(0).id());
            assertEquals(id2, summary.get(1).id());
            assertEquals(id1, summary.get(2).id());
        } catch (InvalidTeamNameException | GameNotFoundException | InvalidScoreException e) {
            e.printStackTrace();
        }
    }

}
