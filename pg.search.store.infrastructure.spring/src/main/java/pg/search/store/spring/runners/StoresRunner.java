package pg.search.store.spring.runners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.store.StoreData;
import pg.search.store.domain.store.Stores;
import pg.search.store.infrastructure.store.StoreService;

import java.util.Collections;

@Slf4j
@AllArgsConstructor
@Component
public class StoresRunner implements ApplicationRunner {
    private final StoreService storeService;

    @Override
    public void run(final ApplicationArguments args) {
        final var store = StoreData.builder()
                .storePhotoId(Files.getDefaultStorePhoto())
                .ratingCount(0)
                .ratingScore(0.0f)
                .website("")
                .phone("")
                .name("Default Unknown Store")
                .offersIds(Collections.emptyList())
                .build();

        Stores.setDefaultStore(storeService.initStore(store));
    }
}