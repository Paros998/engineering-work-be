package pg.search.store.infrastructure.product.laptop;

import pg.search.store.domain.product.laptop.LaptopData;

import java.util.UUID;

public interface LaptopService {
    LaptopEntity getLaptopById(UUID productId);

    LaptopEntity addLaptop(LaptopData data);
}
