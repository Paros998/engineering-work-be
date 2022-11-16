package pg.search.store.application.cqrs.product.query.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.BasicProduct;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class BasicCardQuery implements Query<BasicProduct> {
    private final UUID cardId;
    private UUID userId;
}
