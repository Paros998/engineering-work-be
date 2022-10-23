package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class DeleteCardCommand implements Command<Void> {
    private UUID cardId;
}
