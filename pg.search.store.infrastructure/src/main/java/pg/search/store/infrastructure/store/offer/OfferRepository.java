package pg.search.store.infrastructure.store.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {
    @Query("select o from OfferEntity o where o.product.productId = ?1")
    List<OfferEntity> findByProductProductId(UUID productId);
}