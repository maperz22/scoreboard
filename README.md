# Live Football World Cup Score Board

This project implements a simple library to manage a live football World Cup score board. The score board allows starting new games, updating scores, finishing games, and retrieving a summary of ongoing games.

## Features

- **Start a New Game**: Starts a new game with an initial score of 0-0.
- **Update Score**: Updates the score of an ongoing game.
- **Finish Game**: Finishes an ongoing game and removes it from the score board.
- **Get Summary**: Retrieves a summary of ongoing games, ordered by their total score. Games with the same total score are ordered by the most recently started game.

## Assumptions

- Each team name must be unique and non-empty.
- The home team and away team must be different.
- Scores cannot be negative.
- The total score for sorting purposes is the sum of the home team's score and the away team's score.
- The application is a simple library and does not include a REST API, command line interface, or web service.

## Design Decisions

- **In-Memory Storage**: The application uses an in-memory store (`HashMap`) to keep track of ongoing games. This allows for fast access and manipulation of games. Game IDs are generated using `UUID.randomUUID()`.
- **Custom Exceptions**: Custom checked exceptions are used to handle various error scenarios, ensuring clear error messages and robust error handling.
- **SOLID Principles**: The solution follows SOLID principles to ensure clean and maintainable code.
- **Test-Driven Development (TDD)**: The project was developed using TDD to ensure high code quality and comprehensive test coverage.

## Exception Handling

The application defines custom checked exceptions to handle specific error scenarios:
- `InvalidTeamNameException`: Thrown when a team name is invalid (null, blank, or duplicate).
- `GameNotFoundException`: Thrown when a game is not found.
- `InvalidScoreException`: Thrown when a score is invalid (negative or the same as the current score).

## Usage

The `Scoreboard` class provides the main interface for managing the live football World Cup score board. Here is an example of how to use the score board:

```java

// Create a new score board using factory method
Scoreboard scoreboard = ScoreboardFactory.createScoreboard();

// Start a new game
UUID gameId = scoreboard.startGame("Mexico", "Canada");

// Update the score
scoreboard.updateScore(gameId, 3, 2);

// Finish the game
scoreboard.finishGame(gameId);

// Get the summary of ongoing games
List<Game> summary = scoreboard.getSummary();
summary.forEach(game -> System.out.println(game.homeTeam().name() + " " + game.score().homeScore() + " - " + game.awayTeam().name() + " " + game.score().awayScore()));

```

## Testing

The project includes unit tests for the `ScoreBoard` class and custom exceptions. The tests cover various scenarios, including starting games, updating scores, finishing games, and retrieving summaries. The tests can be run using the following command:

```bash
mvn test
```
    

## Notes

- The library uses Maven as the build tool.
- The library uses JUnit 5 for testing.
- The library uses Java 21.
- The library is not thread-safe and is designed for single-threaded use.
- The library does not include a main method or a user interface.

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.


