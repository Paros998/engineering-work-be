package pg.search.store.infrastructure.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pg.search.store.domain.product.ProductType;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Query("select p from ProductEntity p where p.productId in ?1")
    List<ProductEntity> findByProductIdIn(Collection<UUID> productIds);

    @Query("select p from ProductEntity p where p.productId in ?1 and p.productType = ?2")
    List<ProductEntity> findByProductIdInAndProductType(Collection<UUID> productIds, ProductType productType);

    @Query("select p from ProductEntity p where p.productType = ?1")
    Page<ProductEntity> getProducts(ProductType productType, Pageable pageable);
}