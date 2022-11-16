package pg.search.store.infrastructure.product.console;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {
    private final ConsoleRepository consoleRepository;
}