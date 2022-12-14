package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.ProductService;

@Service
@AllArgsConstructor
public class ProductsQueryHandler implements QueryHandler<ProductsQuery, PageResponse<BasicProduct>> {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public PageResponse<BasicProduct> handle(final ProductsQuery query) {
        String productType = query.getProductType();
        ProductType type = null;

        if (ProductType.isValidType(productType))
            type = ProductType.valueOf(productType);

        final var products = productService.getProducts(PageMapper.toSpringPageRequest(query.getPageRequest()), type, query.getSearchQuery());

        return PageMapper.toPageResponse(products, product -> productMapper.toBasicProduct(product, query.getUserId()));
    }
}