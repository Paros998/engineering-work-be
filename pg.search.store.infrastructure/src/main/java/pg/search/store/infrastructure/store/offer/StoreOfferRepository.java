package pg.search.store.infrastructure.store.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StoreOfferRepository extends JpaRepository<StoreOfferEntity, UUID> {
    @Query("select s from StoreOfferEntity s where s.product.productId = ?1")
    List<StoreOfferEntity> findByProductProductId(UUID productId);

}