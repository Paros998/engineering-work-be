package pg.search.store.infrastructure.store;

import lombok.AllArgsConstructor;

import pg.search.store.infrastructure.common.exception.EntityNotFoundException;

@AllArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    public StoreEntity getStoreByName(final String name) {
        return storeRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Store with name: %s not found".formatted(name), StoreEntity.class)
        );
    }
}
