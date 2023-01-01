package pg.search.store.application.cqrs.product.query.console;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.console.ConsoleEntity;
import pg.search.store.infrastructure.product.console.ConsoleService;

@Service
@AllArgsConstructor
public class ConsoleDataQueryHandler implements QueryHandler<ConsoleDataQuery, ConsoleData> {
    private final ConsoleService consoleService;
    private final ProductMapper productMapper;

    @Override
    public ConsoleData handle(final ConsoleDataQuery query) {
        ConsoleEntity console = consoleService.getConsole(query.getProductId());
        return productMapper.toConsoleData(console);
    }
}
