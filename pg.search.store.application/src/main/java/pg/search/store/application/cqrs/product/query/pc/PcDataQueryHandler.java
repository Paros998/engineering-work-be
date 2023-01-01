package pg.search.store.application.cqrs.product.query.pc;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.pc.PcData;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.pc.PcEntity;
import pg.search.store.infrastructure.product.pc.PcService;

@Service
@AllArgsConstructor
public class PcDataQueryHandler implements QueryHandler<PcDataQuery, PcData> {
    private final PcService pcService;
    private final ProductMapper productMapper;

    @Override
    public PcData handle(final PcDataQuery query) {
        PcEntity pc = pcService.getPcById(query.getProductId());
        return productMapper.toPcData(pc);
    }
}
