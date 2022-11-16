package pg.search.store.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Data
public class GamesFilter {
    private List<Game> games;
    private List<String> chosenPlatforms;
}
