package pg.search.store.infrastructure.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<HistoryEntity, UUID>, JpaSpecificationExecutor<HistoryEntity> {
    @Query("select h from HistoryEntity h where h.user.userId = ?1")
    Page<HistoryEntity> findByUserUserId(UUID userId, Pageable pageable);

}