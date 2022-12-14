package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.query.ProductExistsQuery;
import pg.search.store.application.cqrs.product.query.ProductQuery;
import pg.search.store.application.cqrs.product.query.ProductsByGamesQuery;
import pg.search.store.application.cqrs.product.query.ProductsQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.PRODUCT_PATH)
@AllArgsConstructor
@Tag(name = "Product")
public class ProductHttpEndpoint {
    public static final String SEARCH_PATH = "search-products";
    private final ServiceExecutor serviceExecutor;

    @GetMapping("/{productId}")
    public BasicProduct getProduct(final @PathVariable UUID productId) {
        final var query = ProductQuery.of(productId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping
    public PageResponse<BasicProduct> getProducts(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "asc") String sortDir,
            final @RequestParam(required = false, defaultValue = "title") String sortBy,
            final @RequestParam(required = false) UUID userId,
            final @RequestParam(required = false) String productType,
            final @RequestParam(required = false) String searchQuery
    ) {
        final var queryString = (searchQuery == null || searchQuery.isBlank()) ? null : searchQuery;
        final ProductsQuery query = ProductsQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy), productType, queryString,
                userId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping(SEARCH_PATH + "/by-games")
    public PageResponse<BasicProductWithPerformance> searchProducts(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "asc") String sortDir,
            final @RequestParam(required = false, defaultValue = "title") String sortBy,
            final @RequestParam(required = false) UUID userId,
            final @RequestParam(required = false) String cacheMeta,
            final @Valid @RequestBody GamesFilter filter
    ) {
        final ProductsByGamesQuery query = ProductsByGamesQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy),
                filter, userId, cacheMeta);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("/ProductExistsQuery")
    public Boolean isProductExisting(final @NonNull @RequestBody @Valid ProductExistsQuery query) {
        return serviceExecutor.executeQuery(query);
    }
}
