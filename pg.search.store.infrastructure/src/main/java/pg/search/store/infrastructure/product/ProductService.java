package pg.search.store.infrastructure.product;

import org.springframework.web.multipart.MultipartFile;

import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.Performance;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.filters.ResolvedFilter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {
    SpringPageResponse<ProductEntity> getProducts(SpringPageRequest request, ProductType productType);

    List<ProductEntity> getProductsByIds(List<UUID> products);

    ProductEntity getEntityById(UUID productId);

    String getProductPhoto(ProductEntity entity);

    String getProductPhotoById(UUID productId);

    void uploadProductPhoto(UUID productId, MultipartFile file);

    void updateProductPhoto(UUID productId, MultipartFile file);

    void deleteProductPhoto(UUID productId);

    ResolvedFilter resolveFilter(GamesFilter filter, String queryMeta);

    Map<Class<? extends ProductEntity>, Performance> resolveTargetGamesPerformance(GamesFilter filter, String queryMeta);

    SpringPageResponse<ProductEntity> queryData(ResolvedFilter filter);
}