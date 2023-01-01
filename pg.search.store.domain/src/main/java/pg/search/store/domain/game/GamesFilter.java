package pg.search.store.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.search.store.domain.product.Console;
import pg.search.store.domain.product.ProductType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Data
public class GamesFilter {
    private List<Game> games;
    private List<Console> consoles;
    private ProductType productType;
}
