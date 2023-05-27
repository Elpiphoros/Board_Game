package boardgame.record;

import lombok.*;

/**
 * The basic data of the game result that will be stored.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {

    @NonNull private String player1;
    @NonNull private String player2;
    @NonNull private String winner;
    @NonNull private int operations1;
    @NonNull private int operations2;
    @NonNull private String startOfGame;
    @NonNull private String duration;

}
