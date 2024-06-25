package org.maperz.scoreboard.infrastructure;

import org.maperz.scoreboard.domain.model.Match;
import org.maperz.scoreboard.domain.model.Score;
import org.maperz.scoreboard.domain.model.Team;
import org.maperz.scoreboard.domain.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryMatchRepository implements MatchRepository {
    private final List<Match> matches = new ArrayList<>();

    @Override
    public void save(final Match match) {
        matches.add(match);
    }

    @Override
    public Optional<Match> findByTeams(final Team homeTeam, final Team awayTeam) {
        return matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    @Override
    public List<Match> findByScore(final Score score) {
        return matches.stream()
                .filter(match -> match.getScore().equals(score))
                .collect(Collectors.toList());
    }

    @Override
    public List<Match> findAll() {
        return List.copyOf(matches);
    }

    @Override
    public void delete(final Match match) {
        matches.remove(match);
    }
}
