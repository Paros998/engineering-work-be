package pg.search.store.infrastructure.store.offer;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.notification.NotificationType;
import pg.search.store.domain.store.OfferData;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductRepository;
import pg.search.store.infrastructure.store.StoreEntity;
import pg.search.store.infrastructure.store.StoreRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final NotificationService notificationService;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CurrencyProvider currencyProvider;

    public List<OfferEntity> findStoresByIds(final Collection<UUID> offersIds) {
        return offerRepository.findAllById(offersIds);
    }

    public UUID addOffer(final OfferData data) {
        if (currencyProvider.isInvalidCurrency(data.getCurrency())) {
            throw new IllegalArgumentException(String.format("Currency %s is Invalid", data.getCurrency()));
        }

        Optional<ProductEntity> product = productRepository.findById(data.getProductId());
        Optional<StoreEntity> store = storeRepository.findById(data.getStoreId());

        if (product.isEmpty()) {
            throw new EntityNotFoundException(data.getProductId(), ProductEntity.class);
        }

        if (store.isEmpty()) {
            throw new EntityNotFoundException(data.getStoreId(), StoreEntity.class);
        }

        final var offer = offerRepository.save(toOfferEntity(data, product.get(), store.get()));

        checkNotificationPropagation(offer);

        return offer.getOfferId();
    }

    public OfferEntity toOfferEntity(final OfferData data, final ProductEntity product, final StoreEntity store) {
        return OfferEntity.builder()
                .offerWebsite(data.getOfferWebsite())
                .hasFreeShipping(data.getHasFreeShipping())
                .currency(data.getCurrency())
                .price(data.getPrice())
                .product(product)
                .store(store)
                .build();
    }


    private void checkNotificationPropagation(OfferEntity newOffer) {
        List<OfferEntity> productOffers = newOffer.getProduct().getOnlineOfferList()
                .stream().filter(offer -> !offer.getOfferId().equals(newOffer.getOfferId()))
                .toList();

        String data = newOffer.getProduct().getProductId().toString();
        if (productOffers.isEmpty()) {
            notificationService.fireNotifications(NotificationType.FOLLOWED_AVAILABLE, data);
            notificationService.fireNotifications(NotificationType.MARKED_AVAILABLE, data);
        }

        if (!productOffers.isEmpty()) {
            Optional<OfferEntity> lowestPriceOffer = productOffers.stream()
                    .min(Comparator.comparingDouble(OfferEntity::getPrice))
                    .or(Optional::empty);

            if (!lowestPriceOffer.isEmpty() && lowestPriceOffer.get().getPrice() > newOffer.getPrice()) {
                notificationService.fireNotifications(NotificationType.FOLLOWED_LOWER_PRICE, data);
                notificationService.fireNotifications(NotificationType.MARKED_LOWER_PRICE, data);
            }
        }

    }

}