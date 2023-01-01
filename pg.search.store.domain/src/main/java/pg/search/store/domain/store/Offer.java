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
public class Offer {
    private String name;
    private String phone;
    private String currency;
    private String price;
    private String offerWebsite;
    private UUID storePhoto;
    private Integer ratingCount;
    private Float ratingScore;
    private Boolean hasFreeShipping;
}