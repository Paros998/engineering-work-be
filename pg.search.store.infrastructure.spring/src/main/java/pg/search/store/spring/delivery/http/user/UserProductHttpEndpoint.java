package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.query.GetUserFollowedProductsQuery;
import pg.search.store.domain.product.BasicProduct;

import java.util.List;
import java.util.UUID;

import static pg.search.store.spring.delivery.http.common.HttpCommonHelper.USER_PATH;

@RestController
@RequestMapping(USER_PATH)
@AllArgsConstructor
@Tag(name = "User-Product")
public class UserProductHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{userId}/followed-products")
    public List<BasicProduct> getUserFollowedProducts(final @PathVariable UUID userId) {
        final GetUserFollowedProductsQuery query = GetUserFollowedProductsQuery.of(userId);
        return serviceExecutor.executeQuery(query);
    }

//    @PutMapping("{userId}/follow-card/{cardId}")
//    public void addCardToUserFollowed(final @PathVariable UUID userId, final @PathVariable UUID cardId) {
//        userService.addCardToUserFollowed(userId, cardId);
//    }
//
//    @PutMapping("{userId}/unfollow-card/{cardId}")
//    public void removeCardFromUserFollowed(final @PathVariable UUID userId, final @PathVariable UUID cardId) {
//        userService.removeCardFromUserFollowed(userId, cardId);
//    }
}
