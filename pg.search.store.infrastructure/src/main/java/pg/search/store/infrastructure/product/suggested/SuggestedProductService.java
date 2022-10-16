package pg.search.store.infrastructure.product.suggested;

import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.List;

public interface SuggestedProductService {
    List<SuggestedProductEntity> getAllProducts();

    SpringPageResponse<SuggestedProductEntity> getProducts(SpringPageRequest pageRequest);

    SpringPageResponse<SuggestedProductEntity> getProducts(SpringPageRequest pageRequest, ProductType type);
}