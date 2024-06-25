package org.maperz.scoreboard.application;

import org.maperz.scoreboard.domain.model.Match;

import java.util.List;

public interface ScoreboardService {

    void startMatch(final String homeTeamName, final String awayTeamName);
    void updateScore(final String homeTeamName, final String awayTeamName, final int homeScore, final int awayScore);
    void finishMatch(final String homeTeamName, final String awayTeamName);
    List<Match> getMatches();

    List<Match> getSummary();
}
