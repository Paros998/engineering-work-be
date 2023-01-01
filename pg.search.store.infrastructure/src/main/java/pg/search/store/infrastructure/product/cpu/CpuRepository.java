package pg.search.store.infrastructure.product.cpu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CpuRepository extends JpaRepository<CpuEntity, UUID>, JpaSpecificationExecutor<CpuEntity>, QueryByExampleExecutor<CpuEntity> {

    @Query("select c from CpuEntity c where c.cpuModel in ?1")
    List<CpuEntity> findMatchesByModel(Collection<String> cpuModels);

    @Query("select (count(c) > 0) from CpuEntity c where c.title = ?1")
    boolean existsByTitle(String title);
}