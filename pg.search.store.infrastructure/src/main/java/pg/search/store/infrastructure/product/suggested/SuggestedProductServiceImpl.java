package pg.search.store.infrastructure.product.suggested;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class SuggestedProductServiceImpl implements SuggestedProductService {
    private final SuggestedProductRepository productRepository;

    public List<SuggestedProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public SpringPageResponse<SuggestedProductEntity> getProducts(final SpringPageRequest pageRequest) {
        return new SpringPageResponse<>(productRepository.findAll(pageRequest.getRequest(SuggestedProductEntity.class)));
    }

    public SpringPageResponse<SuggestedProductEntity> getProducts(final SpringPageRequest pageRequest, final ProductType type) {
        return new SpringPageResponse<>(productRepository.findByProductType(type, pageRequest.getRequest(SuggestedProductEntity.class)));
    }
}