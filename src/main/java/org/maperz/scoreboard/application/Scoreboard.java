package org.maperz.scoreboard.application;

import org.maperz.scoreboard.domain.exception.GameNotFoundException;
import org.maperz.scoreboard.domain.exception.InvalidScoreException;
import org.maperz.scoreboard.domain.exception.InvalidTeamNameException;
import org.maperz.scoreboard.domain.model.Game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The Scoreboard interface represents a scoreboard for a game.
 * It provides methods to start a Game, update the score, finish a Game, and retrieve Games.
 */
public interface Scoreboard {

    /**
     * Starts a Game between the given teams.
     *
     * @param homeTeamName The name of the home team.
     * @param awayTeamName The name of the away team.
     * @return The unique identifier of the started Game.
     * @throws InvalidTeamNameException If the team name is invalid.
     */
    UUID startGame(final String homeTeamName, final String awayTeamName) throws InvalidTeamNameException;

    /**
     * Starts a Game between the given teams at the specified start time.
     *
     * @param homeTeamName The name of the home team.
     * @param awayTeamName The name of the away team.
     * @param startTime The start time of the Game.
     * @return The unique identifier of the started Game.
     * @throws InvalidTeamNameException If the team name is invalid.
     */
    UUID startGame(final String homeTeamName, final String awayTeamName, final LocalDateTime startTime) throws InvalidTeamNameException;

    /**
     * Updates the score of the Game identified by the given id.
     *
     * @param id The unique identifier of the Game.
     * @param homeScore The updated score of the home team.
     * @param awayScore The updated score of the away team.
     * @throws GameNotFoundException If the Game is not found.
     * @throws InvalidScoreException If the score is invalid.
     */
    void updateScore(final UUID id, final int homeScore, final int awayScore) throws GameNotFoundException, InvalidScoreException;

    /**
     * Finishes the Game identified by the given id.
     *
     * @param id The unique identifier of the Game.
     * @throws GameNotFoundException If the Game is not found.
     */
    void finishGame(final UUID id) throws GameNotFoundException;

    /**
     * Retrieves a list of all Games.
     *
     * @return A list of all Games.
     */
    List<Game> getGames();

    /**
     * Retrieves a sorted summary of all Games.
     * The Games are sorted by total score.
     * If two Games have the same total score, they are sorted by start time.
     *
     * @implNote The Games are sorted in descending order of total score.
     *
     * @return A list of all Games with their summary.
     */
    List<Game> getSummary();
}
