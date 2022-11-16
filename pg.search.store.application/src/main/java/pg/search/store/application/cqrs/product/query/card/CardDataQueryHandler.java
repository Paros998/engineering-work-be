package pg.search.store.application.cqrs.product.query.card;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardService;

@Service
@AllArgsConstructor
public class CardDataQueryHandler implements QueryHandler<CardDataQuery, CardData> {
    private final CardService cardService;
    private final ProductMapper productMapper;

    public CardData handle(final CardDataQuery query) {
        CardEntity card = cardService.getCardById(query.getCardId());

        return productMapper.toCardData(card);
    }
}
