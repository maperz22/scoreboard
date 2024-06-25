package org.maperz.scoreboard.domain.repository;

import org.maperz.scoreboard.domain.model.Match;
import org.maperz.scoreboard.domain.model.Score;
import org.maperz.scoreboard.domain.model.Team;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void save(final Match match);
    Optional<Match> findByTeams(final Team homeTeam, final Team awayTeam);
    List<Match> findByScore(final Score score);
    List<Match> findAll();
    void delete(final Match match);
}
