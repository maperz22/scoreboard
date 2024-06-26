package org.maperz.scoreboard.domain.validator;

import org.maperz.scoreboard.domain.exception.GameNotFoundException;
import org.maperz.scoreboard.domain.exception.InvalidScoreException;
import org.maperz.scoreboard.domain.exception.InvalidTeamNameException;
import org.maperz.scoreboard.domain.model.Game;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Validates game data.
 * <p>
 *     This class provides methods to validate game data.
 *     It is used to validate the score, team names, and id of a game.
 *     It is a utility class and cannot be instantiated.
 *     All methods are static.
 *     The class has a private constructor to prevent instantiation.
 *     The class is final to prevent extension.
 * </p>
 */
public final class GameValidator {

    /**
     * Validates the score of a game.
     *
     * @param homeScore the home team score
     * @param awayScore the away team score
     * @param game the game to validate
     * @throws InvalidScoreException if the score is invalid
     */
    public static void validateScore(final int homeScore, final int awayScore, final Game game) throws InvalidScoreException {
        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidScoreException("Score must be greater than or equal to 0");
        }
        if (game.score().homeScore() == homeScore && game.score().awayScore() == awayScore) {
            throw new InvalidScoreException("Score must be different");
        }
    }

    /**
     * Validates the team names of a game.
     *
     * @param homeTeamName the home team name
     * @param awayTeamName the away team name
     * @param games the list of games to validate against
     * @throws InvalidTeamNameException if the team names are invalid
     */
    public static void validateTeamNames(final String homeTeamName, final String awayTeamName, List<Game> games) throws InvalidTeamNameException {
        validateTeamName(homeTeamName);
        validateTeamName(awayTeamName);
        if (homeTeamName.equalsIgnoreCase(awayTeamName)) {
            throw new InvalidTeamNameException("Home team name and away team name must not be the same");
        }
        if (games.stream().anyMatch(g -> g.homeTeam().name().equalsIgnoreCase(homeTeamName) || g.awayTeam().name().equalsIgnoreCase(awayTeamName))) {
            throw new InvalidTeamNameException("Match already exists with the same team names");
        }
        if (games.stream().anyMatch(g -> g.homeTeam().name().equalsIgnoreCase(awayTeamName) || g.awayTeam().name().equalsIgnoreCase(homeTeamName))) {
            throw new InvalidTeamNameException("Match already exists with reversed team names");
        }
    }

    /**
     * Validates a team name.
     *
     * @param teamName the team name to validate
     * @throws InvalidTeamNameException if the team name is invalid
     */
    private static void validateTeamName(final String teamName) throws InvalidTeamNameException {
        if (teamName == null || teamName.isBlank()) {
            throw new InvalidTeamNameException("Team name must not be null or empty");
        }
    }

    /**
     * Validates the id of a game.
     *
     * @param id the id to validate
     * @param games the map of games to validate against
     * @throws GameNotFoundException if the game is not found
     */
    public static void validateId(final UUID id, final Map<UUID, Game> games) throws GameNotFoundException {
        if (!games.containsKey(id)) {
            throw new GameNotFoundException("Match not found with id: " + id);
        }
    }

    private GameValidator() {
    }


}
