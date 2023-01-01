package pg.search.store.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    UserEntity getByUsername(String username);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.currency = ?1 where u.userId = ?2")
    void updateCurrencyByUserId(String currency, UUID userId);

    @Query("select u from UserEntity u where u.enabled = true and u.userSettings.isNewProductAdded = true")
    List<UserEntity> findActiveUsersWithNewProductSubscription();

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasFollowedProductNewReview = true and ?1 member u.followedProducts""")
    List<UserEntity> findActiveUsersWithFollowedProductNewReviewSubscriptionThatFollow(UUID productId);

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasFollowedProductNewReview = true and ?1 member u.markedForBuyProducts""")
    List<UserEntity> findActiveUsersWithMarkedProductNewReviewSubscriptionThatMarked(UUID productId);

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasFollowedProductBecomeAvailableOnline = true and ?1 member u.followedProducts""")
    List<UserEntity> findActiveUsersWithFollowedProductAvailableSubscriptionThatFollow(UUID productId);

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasMarkedProductBecomeAvailableOnline = true and ?1 member u.markedForBuyProducts""")
    List<UserEntity> findActiveUsersWithMarkedProductAvailableSubscriptionThatMarked(UUID productId);

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasFollowedProductLowerPriceOffer = true and ?1 member u.followedProducts""")
    List<UserEntity> findActiveUsersWithFollowedProductLowerPriceOfferSubscriptionThatFollow(UUID productId);

    @Query("""
            select u from UserEntity u
            where u.enabled = true and u.userSettings.hasMarkedProductLowerPriceOffer = true and ?1 member u.markedForBuyProducts""")
    List<UserEntity> findActiveUsersWithMarkedProductLowerPriceOfferSubscriptionThatMarked(UUID productId);
}