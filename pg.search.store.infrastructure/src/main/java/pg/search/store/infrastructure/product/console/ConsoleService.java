package pg.search.store.infrastructure.product.console;

import pg.search.store.domain.product.console.ConsoleData;

import java.util.UUID;

public interface ConsoleService {
    ConsoleEntity addConsole(ConsoleData data);

    ConsoleEntity getConsole(UUID productId);
}
