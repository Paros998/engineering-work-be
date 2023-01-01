package pg.search.store.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import pg.search.store.domain.product.ProductType;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
    @Query("select p from ProductEntity p where p.productId in ?1")
    List<ProductEntity> findByProductIdIn(Collection<UUID> productIds);

    @Query("select p from ProductEntity p where p.productId in ?1 and p.productType = ?2")
    List<ProductEntity> findByProductIdInAndProductType(Collection<UUID> productIds, ProductType productType);

    @Query("select (count(p) > 0) from ProductEntity p where p.productId = ?1 and p.productType = ?2")
    boolean existsByProductIdAndProductType(UUID productId, ProductType productType);


}