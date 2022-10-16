package pg.search.store.infrastructure.product;

import pg.search.store.domain.product.BasicProduct;

public interface ProductMapper {
    BasicProduct toBasicProduct(ProductEntity product);
}
