package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.store.offer.StoreOfferEntity;
import pg.search.store.infrastructure.store.offer.StoreOfferRepository;
import pg.search.store.infrastructure.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final StoreOfferRepository offerRepository;
    private final ProductService productService;
    private final UserService userService;

    public BasicProduct toBasicProduct(final @NonNull ProductEntity product, final UUID userId) {
        final UUID productId = product.getProductId();
        final List<StoreOfferEntity> offers = offerRepository.findByProductProductId(productId);

        final Double lowestPrice = offers.stream()
                .min(Comparator.comparingDouble(StoreOfferEntity::getPrice))
                .map(StoreOfferEntity::getPrice)
                .orElse(0d);

        return BasicProduct.builder()
                .productId(productId)
                .title(product.getTitle())
                .productType(product.productType)
                .available(!offers.isEmpty())
                .storesNumber(offers.size())
                .storesLowestPrice(lowestPrice)
                .productPhoto(productService.getProductPhoto(product))
                .isFollowed(userService.isUserFollowingProduct(userId, productId))
                .isMarked(userService.hasUserMarkedProduct(userId, productId))
                .build();
    }

    public BasicProductWithPerformance toBasicProductWithPerformance(final @NonNull ProductEntity product, final UUID userId,
                                                                     final Float peakPerformance, final Float baseAvgPerformance) {
        final var basicProduct = (BasicProductWithPerformance) toBasicProduct(product, userId);

        basicProduct.setAvgPerformance(product.getAvgPerformance(baseAvgPerformance))
                .setPeakPerformance(product.getPeakPerformance(peakPerformance));

        return basicProduct;
    }

    public CardData toCardData(final CardEntity card) {
        return CardData.builder()
                .title(card.getTitle())
                .producentCode(card.getProducentCode())
                .producentSite(card.getProducentSite())
                .technology(card.getTechnology())
                .rtxSupport(card.getRtxSupport())
                .supportedLibraries(card.getSupportedLibraries())
                .cudaCoresAmount(card.getCudaCoresAmount())
                .powerConsumption(card.getPowerConsumption())
                .recommendedPower(card.getRecommendedPower())
                .cooling(card.getCooling())
                .powerConnector(card.getPowerConnector())
                .coreClock(card.getCoreClock())
                .boostCoreClock(card.getBoostCoreClock())
                .memoryAmount(card.getMemoryAmount())
                .supportedDirectX(card.getSupportedDirectX())
                .typeOfMemory(card.getTypeOfMemory())
                .typeOfPciConnector(card.getTypeOfPciConnector())
                .memoryClock(card.getMemoryClock())
                .memoryBus(card.getMemoryBus())
                .build();
    }
}