package pg.search.store.application.cqrs.product.query.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.card.CardData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class GetCardDataQuery implements Query<CardData> {
    private final UUID cardId;
}
