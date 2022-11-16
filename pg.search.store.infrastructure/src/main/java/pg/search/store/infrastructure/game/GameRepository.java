package pg.search.store.infrastructure.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID>, JpaSpecificationExecutor<GameEntity> {
}