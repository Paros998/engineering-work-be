package pg.search.store.infrastructure.product.suggested;

import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductRepository;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SuggestedProductServiceImpl implements SuggestedProductService {
    private final ProductRepository productRepository;
    private final SuggestedProductRepository suggestedProductRepository;

    public List<SuggestedProductEntity> getAllProducts() {
        return suggestedProductRepository.findAll();
    }

    public SpringPageResponse<SuggestedProductEntity> getProducts(final SpringPageRequest pageRequest) {
        return new SpringPageResponse<>(suggestedProductRepository.findAll(pageRequest.getRequest(SuggestedProductEntity.class)));
    }

    public SpringPageResponse<SuggestedProductEntity> getProducts(final SpringPageRequest pageRequest, final ProductType type) {
        if (type == null)
            return getProducts(pageRequest);
        return new SpringPageResponse<>(suggestedProductRepository.findByProductType(type, pageRequest.getRequest(SuggestedProductEntity.class)));
    }

    @PostConstruct
    private void init() {
        changeSuggestedProducts();
    }

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 24L)
    private void changeSuggestedProducts() {
        suggestedProductRepository.deleteAll();

        List<ProductEntity> products = productRepository.findAll();

        if (products.isEmpty())
            return;

        Collections.shuffle(products);

        final var chosenSuggestedProducts = products
                .subList(0, (int) Math.ceil(products.size() / 5.0))
                .stream()
                .map(p -> SuggestedProductEntity.builder()
                        .productId(p.getProductId())
                        .productType(p.getProductType())
                        .build())
                .toList();

        suggestedProductRepository.saveAll(chosenSuggestedProducts);

        // TODO think about statistics gathering and using on these suggested products calculation
    }
}