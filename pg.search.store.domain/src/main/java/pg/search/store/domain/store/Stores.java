package pg.search.store.domain.store;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Stores {
    private static UUID defaultStore;

    public static UUID getDefaultStore() {
        return defaultStore;
    }

    public static void setDefaultStore(UUID defaultStore) {
        Stores.defaultStore = defaultStore;
    }
}