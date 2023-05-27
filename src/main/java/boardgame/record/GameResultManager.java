package boardgame.record;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The GameResultManager interface represents a manager for managing game results.
 * It provides methods for adding a game result, retrieving all game results,
 * and getting the best players based on their number of wins.
 */
public interface GameResultManager {

    /**
     * Adds a game result to the manager.
     *
     * @param result the game result that will be added
     * @return the updated list of game results
     * @throws IOException if an I/O error occurs while adding the game result
     */
    List<GameResult> add(GameResult result) throws IOException;

    /**
     * Retrieves all game results managed by the manager.
     *
     * @return the list of all game results
     * @throws IOException if an I/O error occurs while retrieving the game results
     */
    List<GameResult> getAll() throws IOException;

    /**
     * Retrieves the best players based on their number of wins.
     *
     * @param limit the maximum number of the best players to retrieve
     * @return the list of the best players
     * @throws IOException if an I/O error occurs while retrieving the best players
     */
    default List<PlayerStatistics> getBestPlayers(int limit) throws IOException {
        var winnerMap = getAll()
                .stream()
                .collect(Collectors.groupingBy(GameResult::getWinner, Collectors.counting()));
        return winnerMap.entrySet()
                .stream()
                .map(entry -> new PlayerStatistics(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(PlayerStatistics::getNumberOfWins).reversed())
                .limit(limit)
                .toList();
    }
}
