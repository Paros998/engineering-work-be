package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.RemoveProductFromUserFollowedCommand;
import pg.search.store.application.cqrs.product.command.SaveFollowedProductToUserCommand;
import pg.search.store.application.cqrs.product.query.UserFollowedProductsQuery;
import pg.search.store.application.cqrs.user.query.UserFollowsProductQuery;
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
        final UserFollowedProductsQuery query = UserFollowedProductsQuery.of(userId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{userId}/follows/{productId}")
    public Boolean isUserFollowingProduct(final @PathVariable @NonNull UUID userId, final @PathVariable @NonNull UUID productId) {
        return serviceExecutor.executeQuery(UserFollowsProductQuery.of(userId, productId));
    }

    @PutMapping("{userId}/follow-product/{productId}")
    public void saveProductToUserFollowed(final @PathVariable UUID userId, final @PathVariable UUID productId) {
        final SaveFollowedProductToUserCommand command = SaveFollowedProductToUserCommand.of(userId, productId);
        serviceExecutor.executeCommand(command);
    }

    @DeleteMapping("{userId}/unfollow-product/{productId}")
    public void removeProductFromUserFollowed(final @PathVariable UUID userId, final @PathVariable UUID productId) {
        final RemoveProductFromUserFollowedCommand command = RemoveProductFromUserFollowedCommand.of(userId, productId);
        serviceExecutor.executeCommand(command);
    }
}