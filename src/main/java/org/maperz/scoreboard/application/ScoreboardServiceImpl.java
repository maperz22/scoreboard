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
        if (homeTeamName.equals(awayTeamName)) {
            throw new IllegalArgumentException("Home team and away team must be different");
        }
        matchRepository.findByTeams(new Team(homeTeamName), new Team(awayTeamName))
                .ifPresent(match -> {
                    throw new IllegalArgumentException("Match already exists");
                });
    }
}
