package pg.search.store.infrastructure.product;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.domain.product.GpuData;
import pg.search.store.domain.product.ProcessorData;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.domain.product.pc.PcData;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.console.ConsoleEntity;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.laptop.LaptopEntity;
import pg.search.store.infrastructure.product.pc.PcEntity;

import java.util.UUID;

public interface ProductMapper {
    BasicProduct toBasicProduct(ProductEntity product, UUID userId);

    BasicProductWithPerformance toBasicProductWithPerformance(ProductEntity product, UUID userId, Double peakPerformance,
                                                              Double baseAvgPerformance);

    CardData toCardData(CardEntity card);

    CpuData toCpuData(CpuEntity cpu);

    ConsoleData toConsoleData(ConsoleEntity console);

    PcData toPcData(PcEntity pc);

    LaptopData toLaptopData(LaptopEntity laptop);

    GpuData toGpuData(CardEntity card);

    ProcessorData toProcessorData(CpuEntity cpu);
}