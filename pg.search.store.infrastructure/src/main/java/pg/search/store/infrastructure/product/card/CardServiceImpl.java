package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.product.exception.CardAlreadyExistsException;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public CardEntity addCard(final CardData data) {
        if (cardRepository.existsByTitle(data.getTitle())) {
            throw new CardAlreadyExistsException(data.getTitle());
        }

        final CardEntity cardEntity = (CardEntity) new CardEntity()
                .setProductType(CardData.productType)
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite());
        cardEntity
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
                .setMemoryBus(data.getMemoryBus())
        ;

        cardRepository.save(cardEntity);

        return cardEntity;
    }
}
