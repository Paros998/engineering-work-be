package pg.search.store.infrastructure.store.offer;

import pg.search.store.domain.store.OfferData;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.store.StoreEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface OfferService {
    List<OfferEntity> findStoresByIds(Collection<UUID> offersIds);

    UUID addOffer(OfferData data);

    OfferEntity toOfferEntity(OfferData data, ProductEntity product, StoreEntity store);
}