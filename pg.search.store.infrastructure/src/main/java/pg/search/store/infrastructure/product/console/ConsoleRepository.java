package pg.search.store.infrastructure.product.console;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsoleRepository extends JpaRepository<ConsoleEntity, UUID> {
}