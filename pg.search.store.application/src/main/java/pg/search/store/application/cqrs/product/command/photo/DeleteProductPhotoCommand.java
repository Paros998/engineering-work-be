package pg.search.store.application.cqrs.product.command.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class DeleteProductPhotoCommand implements Command<Void> {
    private final UUID productId;
}
