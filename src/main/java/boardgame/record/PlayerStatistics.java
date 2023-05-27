package boardgame.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * The PlayerStatistics class represents the statistics of a player in a game.
 */
@Data
@AllArgsConstructor
public class PlayerStatistics {

    @NonNull private String winner;
    private long numberOfWins;

}
