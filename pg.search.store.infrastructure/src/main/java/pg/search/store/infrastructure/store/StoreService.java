package pg.search.store.infrastructure.store;

import pg.search.store.domain.store.StoreData;

import java.util.UUID;

public interface StoreService {
    StoreEntity getStoreByName(String name);

    UUID addStore(StoreData data);

    UUID initStore(StoreData data);

    StoreEntity toStoreEntity(final StoreData data);
}
