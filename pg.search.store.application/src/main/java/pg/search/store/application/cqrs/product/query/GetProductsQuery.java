package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.product.BasicProduct;

@AllArgsConstructor(staticName = "of")
@Getter
public class GetProductsQuery implements Query<PageResponse<BasicProduct>> {
    private final PageRequest pageRequest;
    private String productType;
}
