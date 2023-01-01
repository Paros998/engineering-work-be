package pg.search.store.infrastructure.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.store.StoreData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.store.offer.OfferService;

import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final FileService fileService;
    private final OfferService offerService;

    public StoreEntity getStoreByName(final String name) {
        return storeRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Store with name: %s not found".formatted(name), StoreEntity.class)
        );
    }

    public UUID addStore(final StoreData data) {
        final var store = storeRepository.findByName(data.getName());

        if (store.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Store with %s name already exists", data.getName()));
        }

        return storeRepository.save(toStoreEntity(data)).getStoreId();
    }

    public UUID initStore(final StoreData data) {
        final var store = storeRepository.findByName(data.getName());

        if (store.isPresent()) {
            log.warn("Store with {} name already exists", data.getName());
            return store.get().getStoreId();
        }

        StoreEntity newStore = storeRepository.save(toStoreEntity(data));
        return newStore.getStoreId();
    }

    public StoreEntity toStoreEntity(final StoreData data) {
        return StoreEntity.builder()
                .storeId(data.getStoreId())
                .storePhoto(fileService.getFileById(data.getStorePhotoId()))
                .storeOfferList(data.getOffersIds().isEmpty()
                        ? Collections.emptyList() : offerService.findStoresByIds(data.getOffersIds()))
                .website(data.getWebsite())
                .name(data.getName())
                .phone(data.getPhone())
                .ratingCount(data.getRatingCount())
                .ratingScore(data.getRatingScore())
                .build();
    }
}
