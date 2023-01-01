package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.query.ProductOffersQuery;
import pg.search.store.application.cqrs.store.offer.command.AddOfferCommand;
import pg.search.store.domain.store.Offer;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.PRODUCT_OFFERS)
@AllArgsConstructor
@Tag(name = "Product-Offers")
public class ProductOffersHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{productId}")
    public List<Offer> getProductOffers(final @NonNull @PathVariable UUID productId,
                                        final @RequestParam(required = false) UUID userId) {
        final var query = ProductOffersQuery.of(productId, userId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("AddOfferCommand")
    public UUID addProductOffer(final @NonNull @RequestBody @Valid AddOfferCommand command) {
        return serviceExecutor.executeCommand(command);
    }

}