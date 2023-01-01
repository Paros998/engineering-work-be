package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.ProductType;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class ProductExistsQuery implements Query<Boolean> {
    private UUID productId;
    private ProductType productType;
}