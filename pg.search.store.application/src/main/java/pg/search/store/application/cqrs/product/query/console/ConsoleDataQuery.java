package pg.search.store.application.cqrs.product.query.console;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.console.ConsoleData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class ConsoleDataQuery implements Query<ConsoleData> {
    private UUID productId;
}
