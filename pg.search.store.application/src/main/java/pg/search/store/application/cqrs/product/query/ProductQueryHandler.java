package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.ProductService;

@Service
@AllArgsConstructor
public class ProductQueryHandler implements QueryHandler<ProductQuery, BasicProduct> {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public BasicProduct handle(final ProductQuery query) {
        return productMapper.toBasicProduct(productService.getEntityById(query.getProductId()), null);
    }
}