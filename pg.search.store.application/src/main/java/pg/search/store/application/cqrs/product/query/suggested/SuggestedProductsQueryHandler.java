package pg.search.store.application.cqrs.product.query.suggested;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.product.suggested.SuggestedProductEntity;
import pg.search.store.infrastructure.product.suggested.SuggestedProductService;

@Service
@AllArgsConstructor
public class SuggestedProductsQueryHandler implements QueryHandler<SuggestedProductsQuery, PageResponse<BasicProduct>> {
    private final SuggestedProductService suggestedProductService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public PageResponse<BasicProduct> handle(final SuggestedProductsQuery query) {
        String productType = query.getProductType();
        ProductType type = null;

        if (ProductType.isValidType(productType))
            type = ProductType.valueOf(productType);

        final var suggested = suggestedProductService.getProducts(PageMapper.toSpringPageRequest(query.getPageRequest()), type);

        final var suggestedIds = suggested.getContent().stream().map(SuggestedProductEntity::getProductId).toList();

        final var products = SpringPageResponse.<ProductEntity>builder()
                .currentPage(suggested.getCurrentPage())
                .totalPages(suggested.getTotalPages())
                .content(productService.getProductsByIds(suggestedIds))
                .build();

        return PageMapper.toPageResponse(products, product -> productMapper.toBasicProduct(product, query.getUserId()));
    }
}