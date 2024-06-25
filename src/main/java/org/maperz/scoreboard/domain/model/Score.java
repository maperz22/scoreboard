package org.maperz.scoreboard.domain.model;

public class Score {
    private final int homeScore;
    private final int awayScore;

    public Score(final int homeScore, final int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }


}
