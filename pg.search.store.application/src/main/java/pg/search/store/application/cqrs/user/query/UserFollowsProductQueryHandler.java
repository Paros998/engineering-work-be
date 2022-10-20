package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UserFollowsProductQueryHandler implements QueryHandler<UserFollowsProductQuery, Boolean> {
    private final UserService userService;

    public Boolean handle(final UserFollowsProductQuery query) {
        return userService.getUserFollowedProducts(query.getUserId(), null).stream()
                .anyMatch(product -> product.getProductId().equals(query.getProductId()));
    }
}
