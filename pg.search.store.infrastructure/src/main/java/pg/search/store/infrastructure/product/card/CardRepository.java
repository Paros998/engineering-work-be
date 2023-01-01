package pg.search.store.infrastructure.product.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID>, JpaSpecificationExecutor<CardEntity> {
    boolean existsByTitle(String title);

    Optional<CardEntity> findByTitle(String title);

    @Query("select c from CardEntity c where c.cardModel in ?1")
    List<CardEntity> findMatchesByModel(Collection<String> cardModels);
}