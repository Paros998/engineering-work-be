package pg.search.store.application.cqrs.product.query.cpu;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.cpu.CpuService;

@Service
@AllArgsConstructor
public class CpuDataQueryHandler implements QueryHandler<CpuDataQuery, CpuData> {
    private final CpuService cpuService;
    private final ProductMapper productMapper;

    @Override
    public CpuData handle(final CpuDataQuery query) {
        CpuEntity cpu = cpuService.getCpuById(query.getProductId());
        return productMapper.toCpuData(cpu);
    }
}
