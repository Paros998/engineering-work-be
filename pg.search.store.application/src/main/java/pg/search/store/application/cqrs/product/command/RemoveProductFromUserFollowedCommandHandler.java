package pg.search.store.application.cqrs.product.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class RemoveProductFromUserFollowedCommandHandler implements CommandHandler<RemoveProductFromUserFollowedCommand, Void> {
    private final UserService userService;

    public Void handle(final RemoveProductFromUserFollowedCommand command) {
        userService.removeProductFromUserFollowed(command.getUserId(), command.getProductId());
        return null;
    }
}
