package pg.search.store.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserSettingsData {
    private Boolean isNewCardAdded;
    private Boolean hasFollowedProductBecomeAvailableOnline;
    private Boolean hasMarkedProductBecomeAvailableOnline;
    private Boolean hasFollowedProductNewReview;
    private Boolean hasMarkedProductNewReview;
    private Boolean hasFollowedProductLowerPriceOffer;
    private Boolean hasMarkedProductLowerPriceOffer;
}
