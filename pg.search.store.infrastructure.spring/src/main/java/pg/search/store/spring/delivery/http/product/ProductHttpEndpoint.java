package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.query.GetProductsQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.product.BasicProduct;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

@RestController
@RequestMapping(HttpCommonHelper.PRODUCT_PATH)
@AllArgsConstructor
@Tag(name = "Product")
public class ProductHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping
    public PageResponse<BasicProduct> getUsers(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "asc") String sortDir,
            final @RequestParam(required = false, defaultValue = "title") String sortBy,
            final @RequestParam(required = false) String productType
    ) {
        final GetProductsQuery query = GetProductsQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy), productType);
        return serviceExecutor.executeQuery(query);
    }
}
