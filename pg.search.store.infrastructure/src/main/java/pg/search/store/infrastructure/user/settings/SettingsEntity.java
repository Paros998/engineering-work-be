package pg.search.store.infrastructure.user.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pg.search.store.domain.user.UserSettingsData;
import pg.search.store.infrastructure.user.UserEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "settings")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingsEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID settingsId;

    private Boolean isNewCardAdded = false;

    @Column(name = "available")
    private Boolean hasFollowedProductBecomeAvailableOnline = false;

    @Column(name = "marked_available")
    private Boolean hasMarkedProductBecomeAvailableOnline = false;

    @Column(name = "new_review")
    private Boolean hasFollowedProductNewReview = false;

    @Column(name = "marked_new_review")
    private Boolean hasMarkedProductNewReview = false;

    @Column(name = "lower_price")
    private Boolean hasFollowedProductLowerPriceOffer = false;

    @Column(name = "marked_lower_price")
    private Boolean hasMarkedProductLowerPriceOffer = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    public static SettingsEntity of(UserEntity user) {
        return SettingsEntity.builder().user(user).build();
    }

    public void update(final UserSettingsData data) {
        this.setIsNewCardAdded(data.getIsNewCardAdded())
                .setHasFollowedProductBecomeAvailableOnline(data.getHasFollowedProductBecomeAvailableOnline())
                .setHasFollowedProductLowerPriceOffer(data.getHasFollowedProductLowerPriceOffer())
                .setHasFollowedProductNewReview(data.getHasFollowedProductNewReview())
                .setHasMarkedProductBecomeAvailableOnline(data.getHasMarkedProductBecomeAvailableOnline())
                .setHasMarkedProductNewReview(data.getHasMarkedProductNewReview())
                .setHasMarkedProductLowerPriceOffer(data.getHasMarkedProductLowerPriceOffer());
    }

    private SettingsEntity setIsNewCardAdded(Boolean newCardAdded) {
        isNewCardAdded = newCardAdded;
        return this;
    }

    private SettingsEntity setHasFollowedProductBecomeAvailableOnline(Boolean hasFollowedProductBecomeAvailableOnline) {
        this.hasFollowedProductBecomeAvailableOnline = hasFollowedProductBecomeAvailableOnline;
        return this;
    }

    private SettingsEntity setHasMarkedProductBecomeAvailableOnline(Boolean hasMarkedProductBecomeAvailableOnline) {
        this.hasMarkedProductBecomeAvailableOnline = hasMarkedProductBecomeAvailableOnline;
        return this;
    }

    private SettingsEntity setHasFollowedProductNewReview(Boolean hasFollowedProductNewReview) {
        this.hasFollowedProductNewReview = hasFollowedProductNewReview;
        return this;
    }

    private SettingsEntity setHasMarkedProductNewReview(Boolean hasMarkedProductNewReview) {
        this.hasMarkedProductNewReview = hasMarkedProductNewReview;
        return this;
    }

    private SettingsEntity setHasFollowedProductLowerPriceOffer(Boolean hasFollowedProductLowerPriceOffer) {
        this.hasFollowedProductLowerPriceOffer = hasFollowedProductLowerPriceOffer;
        return this;
    }

    private void setHasMarkedProductLowerPriceOffer(Boolean hasMarkedProductLowerPriceOffer) {
        this.hasMarkedProductLowerPriceOffer = hasMarkedProductLowerPriceOffer;
    }
}