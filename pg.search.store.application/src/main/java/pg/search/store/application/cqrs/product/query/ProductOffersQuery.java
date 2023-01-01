package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.store.Offer;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ProductOffersQuery implements Query<List<Offer>> {
    private final UUID productId;
    private final UUID userId;
}
