package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.product.exception.CardAlreadyExistsException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardEntity getCardById(final @NonNull UUID cardId) {
        Optional<CardEntity> cardEntity = cardRepository.findById(cardId);

        if (cardEntity.isPresent())
            return cardEntity.get();
        else throw new EntityNotFoundException(cardId, CardEntity.class);
    }

    public CardEntity addCard(final @NonNull CardData data) {
        final CardEntity cardEntity = new CardEntity()
                .setProductType(CardData.productType);

        updateCardBody(cardEntity, data);

        cardRepository.save(cardEntity);

        return cardEntity;
    }

    public CardEntity tryToEditCard(final @NonNull CardData data) {
        Optional<CardEntity> cardEntity = cardRepository.findByTitle(data.getTitle());

        if (cardEntity.isEmpty())
            return addCard(data);

        final CardEntity card = cardEntity.get();

        updateCardBody(card, data);

        return card;
    }

    public void deleteCardById(final @NonNull UUID cardId) {
        if (cardRepository.existsById(cardId))
            cardRepository.deleteById(cardId);
        else throw new EntityNotFoundException(cardId, CardEntity.class);
    }

    private void updateCardBody(final CardEntity entity, final CardData data) {
        if (entity.getTitle() != null && !entity.getTitle().equals(data.getTitle()) && cardRepository.existsByTitle(data.getTitle())) {
            throw new CardAlreadyExistsException(data.getTitle());
        }
        entity
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setTechnology(data.getTechnology())
                .setRtxSupport(data.getRtxSupport())
                .setSupportedLibraries(data.getSupportedLibraries())
                .setCudaCoresAmount(data.getCudaCoresAmount())
                .setPowerConsumption(data.getPowerConsumption())
                .setRecommendedPower(data.getRecommendedPower())
                .setCooling(data.getCooling())
                .setPowerConnector(data.getPowerConnector())
                .setCoreClock(data.getCoreClock())
                .setBoostCoreClock(data.getBoostCoreClock())
                .setMemoryAmount(data.getMemoryAmount())
                .setSupportedDirectX(data.getSupportedDirectX())
                .setTypeOfMemory(data.getTypeOfMemory())
                .setTypeOfPciConnector(data.getTypeOfPciConnector())
                .setMemoryClock(data.getMemoryClock())
                .setMemoryBus(data.getMemoryBus());
    }
}