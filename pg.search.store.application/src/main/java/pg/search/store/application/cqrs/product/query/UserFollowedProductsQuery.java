package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.ProductType;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "ofAll")
@Getter
public class UserFollowedProductsQuery implements Query<List<BasicProduct>> {
    private final UUID userId;
    private ProductType productType;
}
