package pg.search.store.infrastructure.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<GameEntity, UUID>, JpaSpecificationExecutor<GameEntity> {
    @Query(value = "select g from GameEntity g where g.title in ?1")
    List<GameEntity> findByTitleIn(Collection<String> titles);
}