package boardgame.record;

import lombok.*;
import java.time.LocalDateTime;

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
    @NonNull private LocalDateTime startOfGame;
    @NonNull private String duration;

}
