package pg.search.store.application.cqrs.product.query.photo;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.infrastructure.product.ProductService;

@Service
@AllArgsConstructor
public class ProductPhotoQueryHandler implements QueryHandler<ProductPhotoQuery, String> {
    private final ProductService productService;

    public String handle(final ProductPhotoQuery query) {
        return productService.getProductPhotoById(query.getProductId());
    }
}
