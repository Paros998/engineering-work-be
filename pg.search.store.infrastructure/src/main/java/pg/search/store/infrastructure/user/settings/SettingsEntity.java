package pg.search.store.infrastructure.user.settings;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pg.search.store.infrastructure.user.UserEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "settings")
@Getter
@Setter
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
}