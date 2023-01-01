package pg.search.store.application.cqrs.store.offer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class AddOfferCommand implements Command<UUID> {
    private UUID productId;
    private UUID storeId;
    private Double price;
    private String offerWebsite;
    private Boolean hasFreeShipping;
    private String currency;
}