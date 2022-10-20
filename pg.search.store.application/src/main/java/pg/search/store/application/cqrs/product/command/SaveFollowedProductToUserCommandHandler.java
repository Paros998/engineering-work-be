package pg.search.store.application.cqrs.product.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class SaveFollowedProductToUserCommandHandler implements CommandHandler<SaveFollowedProductToUserCommand, Void> {
    private final UserService userService;

    public Void handle(final SaveFollowedProductToUserCommand command) {
        userService.addProductToUserFollowed(command.getUserId(), command.getProductId());
        return null;
    }
}
