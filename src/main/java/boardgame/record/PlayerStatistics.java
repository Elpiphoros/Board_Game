package boardgame.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PlayerStatistics {

    @NonNull private String winner;
    private long numberOfWins;

}
