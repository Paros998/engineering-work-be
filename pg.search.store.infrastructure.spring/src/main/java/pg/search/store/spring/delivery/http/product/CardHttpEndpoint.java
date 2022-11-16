package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.card.CreateCardCommand;
import pg.search.store.application.cqrs.product.command.card.DeleteCardCommand;
import pg.search.store.application.cqrs.product.command.card.EditCardCommand;
import pg.search.store.application.cqrs.product.query.card.BasicCardQuery;
import pg.search.store.application.cqrs.product.query.card.CardDataQuery;
import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.CARD_PATH)
@AllArgsConstructor
@Tag(name = "Product-Card")
public class CardHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{cardId}")
    public CardData getCard(final @NonNull @PathVariable UUID cardId) {
        final CardDataQuery query = CardDataQuery.of(cardId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{cardId}/basic")
    public BasicProduct getBasicCard(final @NonNull @PathVariable UUID cardId, final @RequestParam(required = false) UUID userId) {
        final BasicCardQuery query = BasicCardQuery.of(cardId, userId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("CreateCardCommand")
    public UUID createCard(final @Valid @NonNull @RequestBody CreateCardCommand command) {
        return serviceExecutor.executeCommand(command);
    }

    @PutMapping("EditCardCommand")
    public UUID tryToEditCard(final @Valid @NonNull @RequestBody EditCardCommand command) {
        return serviceExecutor.executeCommand(command);
    }

    @DeleteMapping("DeleteCardCommand")
    public void deleteCard(final @Valid @NonNull @RequestBody DeleteCardCommand command) {
        serviceExecutor.executeCommand(command);
    }
}
