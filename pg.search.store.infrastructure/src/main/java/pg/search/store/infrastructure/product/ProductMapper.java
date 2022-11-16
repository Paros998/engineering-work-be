package pg.search.store.infrastructure.product;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.product.card.CardEntity;

import java.util.UUID;

public interface ProductMapper {
    BasicProduct toBasicProduct(ProductEntity product, UUID userId);

    BasicProductWithPerformance toBasicProductWithPerformance(ProductEntity product, UUID userId, Float peakPerformance,
                                                              Float baseAvgPerformance);

    CardData toCardData(CardEntity card);
}