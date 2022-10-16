package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.infrastructure.store.offer.StoreOfferEntity;
import pg.search.store.infrastructure.store.offer.StoreOfferRepository;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final StoreOfferRepository offerRepository;

    public BasicProduct toBasicProduct(final ProductEntity product) {
        final List<StoreOfferEntity> offers = offerRepository.findByProductProductId(product.productId);

        final Double lowestPrice = offers.stream()
                .min(Comparator.comparingDouble(StoreOfferEntity::getPrice))
                .map(StoreOfferEntity::getPrice)
                .orElse(0d);

        return BasicProduct.builder()
                .productId(product.productId)
                .title(product.getTitle())
                .productType(product.productType)
                .available(!offers.isEmpty())
                .storesNumber(offers.size())
                .storesLowestPrice(lowestPrice)
                .build();
    }
}