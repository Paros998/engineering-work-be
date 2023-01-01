package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.infrastructure.product.ProductRepository;

@Service
@AllArgsConstructor
public class ProductExistsQueryHandler implements QueryHandler<ProductExistsQuery, Boolean> {
    private final ProductRepository repository;

    @Override
    public Boolean handle(final ProductExistsQuery query) {
        return repository.existsByProductIdAndProductType(query.getProductId(), query.getProductType());
    }
}
