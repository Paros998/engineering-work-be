package pg.search.store.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
public class GameWithScore {
    private UUID gameId;
    private String name;
    private String fileUrl;
    private List<Platform> platforms;
    private Map<Platform, Integer> scores;
}
