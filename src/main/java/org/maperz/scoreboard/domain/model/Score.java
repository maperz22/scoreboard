package org.maperz.scoreboard.domain.model;

/**
 * Represents a score in a game.
 */
public record Score(int homeScore, int awayScore) {

    /**
     * @return The total score of the game.
     * @implNote The total score is the sum of the home and away scores.
     */
    public int getTotalScore() {
        return this.homeScore + this.awayScore;
    }

}
