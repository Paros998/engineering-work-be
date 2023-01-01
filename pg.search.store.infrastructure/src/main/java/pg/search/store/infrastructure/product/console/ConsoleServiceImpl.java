package pg.search.store.infrastructure.product.console;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.product.exception.ProductAlreadyExistsException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {
    private final ConsoleRepository consoleRepository;

    public ConsoleEntity addConsole(final ConsoleData data) {
        final var entity = toConsoleEntity(data);

        consoleRepository.save(entity);

        return entity;
    }

    public ConsoleEntity getConsole(final UUID productId) {
        return consoleRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId, ConsoleEntity.class));
    }

    public ConsoleEntity toConsoleEntity(final ConsoleData data) {
        if (data.getTitle() != null && consoleRepository.existsByTitle(data.getTitle())) {
            throw new ProductAlreadyExistsException(data.getTitle());
        }

        ConsoleEntity consoleEntity = new ConsoleEntity();

        return consoleEntity
                .setProductType(ConsoleData.productType)
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setDateOfProduction(CommonData.parseFrom(data.getDateOfProduction()))
                .setConsole(data.getConsole())
                .setProducer(data.getProducer())
                .setPlatform(data.getPlatform());
    }
}