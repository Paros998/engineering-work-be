package pg.search.store.infrastructure.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<StoreEntity, UUID> {
    Optional<StoreEntity> findByName(String name);
}