package pg.search.store.infrastructure.product.card;

import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.List;
import java.util.UUID;

public interface CardService {
    SpringPageResponse<CardEntity> getCardsApplicableForBitcoinMining(SpringPageRequest request, String query);

    CardEntity getCardById(UUID cardId);

    CardEntity addCard(CardData cardData);

    CardEntity tryToEditCard(CardData cardData);

    void deleteCardById(UUID cardId);

    void validateCardModels(List<String> cardModels);
}
