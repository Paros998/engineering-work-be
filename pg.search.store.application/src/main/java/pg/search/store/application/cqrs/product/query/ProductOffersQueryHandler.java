package pg.search.store.application.cqrs.product.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.store.Offer;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.store.StoreEntity;
import pg.search.store.infrastructure.store.offer.OfferEntity;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductOffersQueryHandler implements QueryHandler<ProductOffersQuery, List<Offer>> {
    private final CurrencyProvider currencyProvider;
    private final ProductService productService;
    private final UserRepository userRepository;

    @Override
    public List<Offer> handle(final ProductOffersQuery query) {
        ProductEntity product = productService.getEntityById(query.getProductId());
        Optional<UserEntity> user = query.getUserId() == null ? Optional.empty() : userRepository.findById(query.getUserId());

        String userCurrency = user.isPresent() ? user.get().getCurrency() : CurrencyProvider.DEFAULT_CURRENCY;

        return product.getOnlineOfferList().stream().map(entity -> toOffer(entity, userCurrency)).toList();
    }

    private Offer toOffer(OfferEntity entity, String userCurrency) {
        StoreEntity store = entity.getStore();

        String offerCurrency = entity.getCurrency();
        Double productPriceInUserCurrency = currencyProvider.exchangePrice(offerCurrency, userCurrency, entity.getPrice());

        return Offer.builder()
                .name(store.getName())
                .phone(store.getPhone())
                .currency(offerCurrency)
                .offerWebsite(entity.getOfferWebsite())
                .price(String.format("%.2f", productPriceInUserCurrency))
                .ratingCount(store.getRatingCount())
                .ratingScore(store.getRatingScore())
                .storePhoto(store.getStorePhoto().getFileId())
                .hasFreeShipping(entity.getHasFreeShipping())
                .build();
    }

}