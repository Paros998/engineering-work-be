package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
public class CreateCardCommand extends AbstractCardCommand implements Command<UUID> {
}
