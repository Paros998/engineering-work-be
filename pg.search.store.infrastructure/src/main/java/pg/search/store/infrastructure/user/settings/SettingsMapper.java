package pg.search.store.infrastructure.user.settings;

import lombok.experimental.UtilityClass;

import pg.search.store.domain.user.UserSettingsData;

@UtilityClass
public class SettingsMapper {
    public UserSettingsData toUserSettingsData(final SettingsEntity entity) {
        return UserSettingsData.builder()
                .hasFollowedProductNewReview(entity.getHasFollowedProductNewReview())
                .isNewProductAdded(entity.getIsNewProductAdded())
                .hasMarkedProductNewReview(entity.getHasMarkedProductNewReview())
                .hasMarkedProductBecomeAvailableOnline(entity.getHasMarkedProductBecomeAvailableOnline())
                .hasMarkedProductLowerPriceOffer(entity.getHasMarkedProductLowerPriceOffer())
                .hasFollowedProductLowerPriceOffer(entity.getHasFollowedProductLowerPriceOffer())
                .hasFollowedProductBecomeAvailableOnline(entity.getHasFollowedProductBecomeAvailableOnline())
                .build();
    }
}
