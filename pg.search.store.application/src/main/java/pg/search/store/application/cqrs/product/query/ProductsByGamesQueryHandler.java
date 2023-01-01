package pg.search.store.application.cqrs.product.query;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.domain.product.Performance;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.product.filters.ResolvedFilter;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsByGamesQueryHandler implements QueryHandler<ProductsByGamesQuery, PageResponse<BasicProductWithPerformance>> {
    private final CacheManager cacheManager;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private Cache filterCache;
    private Cache performanceCache;

    @PostConstruct
    void init() {
        filterCache = cacheManager.getCache("filters");
        performanceCache = cacheManager.getCache("performances");
    }

    public PageResponse<BasicProductWithPerformance> handle(final ProductsByGamesQuery query) {
        final String queryMeta = query.getCacheMeta() != null ? query.getCacheMeta() : "ProductsByGamesQuery-" + UUID.randomUUID();

        ResolvedFilter filter;
        try {
            filter = initFilter(query, queryMeta);
        } catch (ResponseStatusException e) {
            if (Objects.equals(e.getMessage(), "No Matches"))
                return new PageResponse<>(1, 1, Collections.emptyList(), null);
            else throw e;
        }

        final var performance = initPerformance(query, queryMeta);

        SpringPageResponse<ProductEntity> products = productService.queryData(filter, PageMapper.toSpringPageRequest(query.getRequest()));

        return PageMapper.toPageResponse(
                products,
                (product -> productMapper.toBasicProductWithPerformance(product, query.getUserId(), performance.getPeakPerformance(),
                        performance.getAveragePerformance())),
                queryMeta
        );
    }

    private ResolvedFilter initFilter(final ProductsByGamesQuery query, final String queryMeta) {
        ResolvedFilter filter = null;

        if (query.getCacheMeta() != null && filterCache != null) {
            filter = filterCache.get(query.getCacheMeta(), ResolvedFilter.class);
        }

        if (filter == null) {
            filter = productService.resolveFilter(query.getFilter(), queryMeta);
        }

        return filter;
    }

    private Performance initPerformance(final ProductsByGamesQuery query, final String queryMeta) {
        Performance performance = null;

        if (query.getCacheMeta() != null && performanceCache != null) {
            performance = performanceCache.get(query.getCacheMeta(), Performance.class);
        }

        if (performance == null) {
            performance = productService.resolveTargetGamesPerformance(query.getFilter(), queryMeta);
        }

        return performance;
    }

}
