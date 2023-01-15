package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.lib.filters.common.Criteria;
import pg.lib.filters.common.JunctionType;
import pg.lib.filters.common.Operation;
import pg.lib.filters.specification.Combiner;
import pg.lib.filters.specification.SpecificationBuilder;
import pg.lib.filters.specification.SpecificationCollector;

import pg.search.store.domain.product.card.CardData;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.exception.ProductAlreadyExistsException;

import java.util.*;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    public SpringPageResponse<CardEntity> getCardsApplicableForBitcoinMining(final SpringPageRequest request, final String query) {
        Map<JunctionType, List<Criteria>> criteria;
        List<SpecificationBuilder> filters = new ArrayList<>();

        final PageRequest pageable = request.getRequest(CardEntity.class);

        criteria = new HashMap<>();

        criteria.put(JunctionType.OR, Collections.singletonList(
                Criteria.builder()
                        .key("bitcoinHashRate")
                        .operation(Operation.NOT_EQUAL_NULL)
                        .build()
        ));

        filters.add(SpecificationBuilder.of(Combiner.AND, criteria));

        if (query != null) {
            criteria = new HashMap<>();

            criteria.put(JunctionType.OR, List.of(
                    Criteria.builder()
                            .key("title").value(query).operation(Operation.MATCH)
                            .build(),
                    Criteria.builder()
                            .key("producentCode").value(query).operation(Operation.MATCH)
                            .build()
            ));

            filters.add(SpecificationBuilder.of(Combiner.AND, criteria));
        }

        Specification<CardEntity> spec = SpecificationCollector.createSpecification(filters);

        return new SpringPageResponse<>(cardRepository.findAll(spec, pageable));
    }

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

    public void validateCardModels(final List<String> cardModels) {
        cardModels.forEach(model -> {
            final var results = cardRepository.findMatchesByModel(Collections.singleton(model));
            if (results.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Required Card Model {%s} that is present on " +
                        "game you're adding has not equivalents in Cards Present in Database", model));
        });
    }

    private void updateCardBody(final CardEntity entity, final CardData data) {
        if (entity.getTitle() != null && !entity.getTitle().equals(data.getTitle()) && cardRepository.existsByTitle(data.getTitle())) {
            throw new ProductAlreadyExistsException(data.getTitle());
        }
        entity
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setDateOfProduction(CommonData.parseFrom(data.getDateOfProduction()))
                .setCardModel(data.getCardModel())
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
                .setMaxNumberOfUnitsInSLI(data.getMaxNumberOfUnitsInSLI())
                .setBitcoinHashRate(data.getBitcoinHashRate());
    }
}