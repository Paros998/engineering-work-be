package pg.search.store.infrastructure.product.pc;

import pg.search.store.domain.product.pc.PcData;

import java.util.UUID;

public interface PcService {
    PcEntity getPcById(UUID productId);

    PcEntity addPc(PcData data);
}
