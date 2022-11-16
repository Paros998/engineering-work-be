package pg.search.store.infrastructure.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    @Query("select r from ReviewEntity r where r.product.productId = ?1")
    Page<ReviewEntity> findByProductId(UUID productId, Pageable pageable);

    @Query("select r from ReviewEntity r where r.product.productId = ?1")
    List<ReviewEntity> findAllByProductId(UUID productId);

    @Query("select r from ReviewEntity r where r.user.userId = ?1 and r.product.productId = ?2")
    Optional<ReviewEntity> findByUserIdAndProductId(UUID userId, UUID productId);

    @Query("select (count(r) > 0) from ReviewEntity r where r.user.userId = ?1 and r.product.productId = ?2")
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    @Transactional
    @Modifying
    @Query(value = "update reviews r set is_censored = NOT r.is_censored where r.review_id = ?1", nativeQuery = true)
    void switchCensoring(UUID reviewId);
}