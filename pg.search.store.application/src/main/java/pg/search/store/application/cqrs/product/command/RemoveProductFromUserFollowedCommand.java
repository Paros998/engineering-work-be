package pg.search.store.application.cqrs.product.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class RemoveProductFromUserFollowedCommand implements Command<Void> {
    private final UUID userId;
    private final UUID productId;
}
