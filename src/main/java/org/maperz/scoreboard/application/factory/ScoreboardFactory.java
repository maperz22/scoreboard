package org.maperz.scoreboard.application.factory;

import org.maperz.scoreboard.application.Scoreboard;
import org.maperz.scoreboard.application.ScoreboardImpl;

/**
 * Factory class for creating a new instance of a Scoreboard.
 */
public class ScoreboardFactory {

    /**
     * Creates a new instance of a Scoreboard.
     *
     * @return a new instance of a Scoreboard.
     */
    public static Scoreboard createScoreboard() {
        return new ScoreboardImpl();
    }

}
