package pg.search.store.infrastructure.product.console;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ConsoleRepository extends JpaRepository<ConsoleEntity, UUID>, JpaSpecificationExecutor<ConsoleEntity> {

    @Query("select (count(c) > 0) from ConsoleEntity c where c.title = ?1")
    boolean existsByTitle(String title);
}