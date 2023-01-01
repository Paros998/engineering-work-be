package pg.search.store.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OfferData {
    private UUID productId;
    private UUID storeId;
    private Double price;
    private String offerWebsite;
    private Boolean hasFreeShipping;
    private String currency;
}