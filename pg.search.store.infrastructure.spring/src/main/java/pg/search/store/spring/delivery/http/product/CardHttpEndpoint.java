package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.card.CreateCardCommand;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.CARD_PATH)
@AllArgsConstructor
@Tag(name = "Product-Card")
public class CardHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @PostMapping("CreateCardCommand")
    public UUID createCard(final @Valid @NonNull @RequestBody CreateCardCommand command) {
        return serviceExecutor.executeCommand(command);
    }
}
