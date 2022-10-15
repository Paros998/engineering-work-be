package pg.search.store.infrastructure.product.suggested;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import pg.search.store.domain.product.ProductType;

import java.util.UUID;

public interface SuggestedProductRepository extends JpaRepository<SuggestedProductEntity, UUID> {
    Page<SuggestedProductEntity> findByProductType(ProductType productType, Pageable pageable);
}
