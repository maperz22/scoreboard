package org.maperz.scoreboard.application;

import org.maperz.scoreboard.domain.validator.GameValidator;
import org.maperz.scoreboard.domain.exception.InvalidScoreException;
import org.maperz.scoreboard.domain.exception.InvalidTeamNameException;
import org.maperz.scoreboard.domain.exception.GameNotFoundException;
import org.maperz.scoreboard.domain.model.Game;
import org.maperz.scoreboard.domain.model.Team;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implementation of the {@link Scoreboard} interface.
 */
public class ScoreboardImpl implements Scoreboard {
    /**
     * Comparator to sort games by start time.
     */
    private static final Comparator<Game> COMPARE_BY_START_TIME = Comparator.comparing(Game::startTime).reversed();

    /**
     * Comparator to sort games by total score.
     */
    private static final Comparator<Game> COMPARE_BY_TOTAL_SCORE = Comparator.comparingInt(game -> game.score().getTotalScore());

    /**
     * The map of games.
     * <p>
     *     The key is the unique identifier of the game.
     *     The value is the game.
     *     The map is mutable and not null.
     *     The map is not thread-safe.
     *     The map is initialized to an empty map.
     *     The map is not exposed to clients.
     * </p>
     *
     * @implNote The map is mutable and not null.
     */
    private final Map<UUID, Game> games = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID startGame(final String homeTeamName, final String awayTeamName) throws InvalidTeamNameException {
        return startGame(homeTeamName, awayTeamName, LocalDateTime.now());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID startGame(final String homeTeamName, final String awayTeamName, final LocalDateTime startTime) throws InvalidTeamNameException {
            GameValidator.validateTeamNames(homeTeamName, awayTeamName, games.values().stream().toList());
            final Team homeTeam = new Team(homeTeamName);
            final Team awayTeam = new Team(awayTeamName);
            final Game game = new Game(homeTeam, awayTeam, startTime);
            games.put(game.id(), game);
            return game.id();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateScore(final UUID id, final int homeScore, final int awayScore) throws GameNotFoundException, InvalidScoreException {
        GameValidator.validateId(id, games);
        final Game game = games.get(id);
        GameValidator.validateScore(homeScore, awayScore, game);
        final Game updatedGame = game.withUpdatedScore(homeScore, awayScore);
        games.put(id, updatedGame);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishGame(final UUID id) throws GameNotFoundException {
        GameValidator.validateId(id, games);
        games.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> getGames() {
        return games.values().stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Game> getSummary() {
        return games.values().stream()
                .sorted(COMPARE_BY_TOTAL_SCORE.reversed().thenComparing(COMPARE_BY_START_TIME))
                .toList();
    }

}
