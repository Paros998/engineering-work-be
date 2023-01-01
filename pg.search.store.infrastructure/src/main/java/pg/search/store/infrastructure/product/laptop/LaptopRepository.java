package pg.search.store.infrastructure.product.laptop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface LaptopRepository extends JpaRepository<LaptopEntity, UUID>, JpaSpecificationExecutor<LaptopEntity> {

    @Query("select (count(l) > 0) from LaptopEntity l where l.title = ?1")
    boolean existsByTitle(String title);
}