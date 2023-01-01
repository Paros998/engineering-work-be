package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.BasicProduct;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ProductQuery implements Query<BasicProduct> {
    private final UUID productId;
}
