package pg.search.store.infrastructure.product;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.product.card.CardEntity;

public interface ProductMapper {
    BasicProduct toBasicProduct(ProductEntity product);

    CardData toCardData(CardEntity card);
}
