package pg.search.store.application.cqrs.product.query.laptop;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.laptop.LaptopService;

@Service
@AllArgsConstructor
public class LaptopDataQueryHandler implements QueryHandler<LaptopDataQuery, LaptopData> {
    private final ProductMapper productMapper;
    private final LaptopService laptopService;

    @Override
    public LaptopData handle(final LaptopDataQuery query) {
        final var laptop = laptopService.getLaptopById(query.getProductId());
        return productMapper.toLaptopData(laptop);
    }
}
