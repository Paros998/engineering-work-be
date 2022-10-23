package pg.search.store.infrastructure.product;

import org.springframework.web.multipart.MultipartFile;

import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.UUID;

public interface ProductService {
    SpringPageResponse<ProductEntity> getProducts(SpringPageRequest request, ProductType productType);

    ProductEntity getEntityById(UUID productId);

    String getProductPhoto(ProductEntity entity);

    String getProductPhotoById(UUID productId);

    void uploadProductPhoto(UUID productId, MultipartFile file);

    void updateProductPhoto(UUID productId, MultipartFile file);

    void deleteProductPhoto(UUID productId);
}