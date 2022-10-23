package pg.search.store.infrastructure.product.card;

import pg.search.store.domain.product.card.CardData;

public interface CardService {
    CardEntity addCard(CardData cardData);
}
