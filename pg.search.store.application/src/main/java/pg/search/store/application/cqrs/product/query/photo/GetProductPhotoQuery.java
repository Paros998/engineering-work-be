package pg.search.store.application.cqrs.product.query.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class GetProductPhotoQuery implements Query<String> {
    private final UUID productId;
}
