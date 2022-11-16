package pg.search.store.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
@Builder
public class Game {
    private String name;
    private List<Platform> platform;
}
