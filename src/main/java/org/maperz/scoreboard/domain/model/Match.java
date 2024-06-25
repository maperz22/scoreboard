package org.maperz.scoreboard.domain.model;

import java.time.LocalDateTime;

public class Match {
    private final Team homeTeam;
    private final Team awayTeam;
    private Score score;
    private final LocalDateTime startTime;

    public Match(final Team homeTeam, final Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = LocalDateTime.now();
        this.score = new Score(0, 0);
    }
    public void setScore(final int homeScore, final int awayScore) {
        this.score = new Score(homeScore, awayScore);
    }
    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public Score getScore() {
        return score;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getTotalScore() {
        return score.getHomeScore() + score.getAwayScore();
    }
}
