package pg.search.store.infrastructure.product.card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GraphicCardRepository extends JpaRepository<GraphicCardEntity, UUID> {
}