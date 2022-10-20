package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.user.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUserFollowedProductsQueryHandler implements QueryHandler<GetUserFollowedProductsQuery, List<BasicProduct>> {
    private final UserService userService;
    private final ProductMapper productMapper;

    public List<BasicProduct> handle(final GetUserFollowedProductsQuery query) {
        return userService.getUserFollowedProducts(query.getUserId(), query.getProductType())
                .stream().map(productMapper::toBasicProduct)
                .toList();
    }
}