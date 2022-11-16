package pg.search.store.application.cqrs.product.query;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsByGamesQueryHandler implements QueryHandler<ProductsByGamesQuery, PageResponse<BasicProductWithPerformance>> {
    private final CacheManager cacheManager;
    final Cache filterCache = cacheManager.getCache("filters");
    final Cache performanceCache = cacheManager.getCache("performances");
    private final ProductService productService;
    private final ProductMapper productMapper;

    public PageResponse<BasicProductWithPerformance> handle(final ProductsByGamesQuery query) {
        final String queryMeta = query.getCacheMeta() != null ? query.getCacheMeta() : "ProductsByGamesQuery-" + UUID.randomUUID();

        final var filter = initFilter(query, queryMeta);

        final var performanceMap = initPerformanceMap(query, queryMeta);

        SpringPageResponse<ProductEntity> products = productService.queryData(filter);

        return PageMapper.toPageResponse(
                products,
                (product -> {
                    Performance performance = performanceMap.get(product.getClass());
                    return productMapper.toBasicProductWithPerformance(product, query.getUserId(), performance.getPeakPerformance(),
                            performance.getAveragePerformance());
                }),
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

    @SuppressWarnings("unchecked")
    private Map<Class<? extends ProductEntity>, Performance> initPerformanceMap(final ProductsByGamesQuery query, final String queryMeta) {
        Map<Class<? extends ProductEntity>, Performance> performanceMap = new HashMap<>();

        if (query.getCacheMeta() != null && performanceCache != null) {
            performanceMap = performanceCache.get(query.getCacheMeta(), performanceMap.getClass());
        }

        if (performanceMap == null) {
            performanceMap = productService.resolveTargetGamesPerformance(query.getFilter(), queryMeta);
        }

        return performanceMap;
    }

}
