package pg.search.store.infrastructure.store.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
 
public interface StoreOfferRepository extends JpaRepository<StoreOfferEntity, UUID> {
}