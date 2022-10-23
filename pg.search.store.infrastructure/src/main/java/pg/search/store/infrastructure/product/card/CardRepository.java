package pg.search.store.infrastructure.product.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    boolean existsByTitle(String title);

    Optional<CardEntity> findByTitle(String title);
}