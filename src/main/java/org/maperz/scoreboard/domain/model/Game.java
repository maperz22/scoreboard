package org.maperz.scoreboard.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a game between two teams.
 */
public record Game(UUID id, Team homeTeam, Team awayTeam, Score score, LocalDateTime startTime) {

    /**
     * Creates a new Game with the given teams and start time.
     *
     * @param homeTeam The home team.
     * @param awayTeam The away team.
     * @param startTime The start time of the Game.
     */
    public Game(Team homeTeam, Team awayTeam, LocalDateTime startTime) {
        this(UUID.randomUUID(), homeTeam, awayTeam, new Score(0, 0), startTime);
    }

    /**
     * Updates the score of the Game.
     *
     * @param homeScore The updated score of the home team.
     * @param awayScore The updated score of the away team.
     * @return The Game with the updated score.
     */
    public Game withUpdatedScore(int homeScore, int awayScore) {
        return new Game(this.id, this.homeTeam, this.awayTeam, new Score(homeScore, awayScore), this.startTime);
    }
}
