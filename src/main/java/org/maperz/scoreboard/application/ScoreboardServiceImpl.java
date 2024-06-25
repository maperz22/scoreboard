package org.maperz.scoreboard.application;

import org.maperz.scoreboard.domain.model.Match;
import org.maperz.scoreboard.domain.model.Team;
import org.maperz.scoreboard.domain.repository.MatchRepository;

import java.util.List;

public class ScoreboardServiceImpl implements ScoreboardService{
    private final MatchRepository matchRepository;

    public ScoreboardServiceImpl(final MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void startMatch(final String homeTeamName, final String awayTeamName) {
        validateTeams(homeTeamName, awayTeamName);
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);
        final Match match = new Match(homeTeam, awayTeam);
        matchRepository.save(match);
    }

    @Override
    public void updateScore(final String homeTeamName, final String awayTeamName, final int homeScore, final int awayScore) {
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);
        final Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
        validateScore(match, homeScore, awayScore);
        match.setScore(homeScore, awayScore);
        matchRepository.save(match);
    }

    @Override
    public void finishMatch(final String homeTeamName, final String awayTeamName) {
        final Team homeTeam = new Team(homeTeamName);
        final Team awayTeam = new Team(awayTeamName);
        final Match match = matchRepository.findByTeams(homeTeam, awayTeam)
                .orElseThrow(() -> new IllegalArgumentException("Match not found"));
        matchRepository.delete(match);
    }

    @Override
    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    private void validateTeams(final String homeTeamName, final String awayTeamName) {
        if (homeTeamName == null || awayTeamName == null) {
            throw new NullPointerException("Team names must not be null");
        }
        if (homeTeamName.isBlank() || awayTeamName.isBlank()) {
            throw new IllegalArgumentException("Team names must not be blank");
        }
        if (homeTeamName.equalsIgnoreCase(awayTeamName)) {
            throw new IllegalArgumentException("Home team and away team must be different");
        }
        matchRepository.findByTeams(new Team(homeTeamName), new Team(awayTeamName))
                .ifPresent(match -> {
                    throw new IllegalArgumentException("Match already exists");
                });
        matchRepository.findByTeams(new Team(awayTeamName), new Team(homeTeamName))
                .ifPresent(match -> {
                    throw new IllegalArgumentException("Match already exists");
                });
    }

    private void validateScore(final Match match, final int homeScore, final int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score must not be negative");
        }
        if (match.getScore().getHomeScore() == homeScore && match.getScore().getAwayScore() == awayScore) {
            throw new IllegalArgumentException("Score must be different");
        }
    }

}
