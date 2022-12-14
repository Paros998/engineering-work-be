package pg.search.store.application.cqrs.product.query.card;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardService;

@Service
@AllArgsConstructor
public class BasicCardQueryHandler implements QueryHandler<BasicCardQuery, BasicProduct> {
    private final CardService cardService;
    private final ProductMapper productMapper;

    public BasicProduct handle(final BasicCardQuery query) {
        CardEntity card = cardService.getCardById(query.getCardId());

        return productMapper.toBasicProduct(card, query.getUserId());
    }
}
