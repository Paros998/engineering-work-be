package pg.search.store.infrastructure.product.pc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PcRepository extends JpaRepository<PcEntity, UUID>, JpaSpecificationExecutor<PcEntity> {
    @Query("select (count(p) > 0) from PcEntity p where p.title = ?1")
    boolean existsByTitle(String title);
}