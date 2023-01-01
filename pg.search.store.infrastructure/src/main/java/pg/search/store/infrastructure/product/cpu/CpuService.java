package pg.search.store.infrastructure.product.cpu;

import pg.search.store.domain.product.cpu.CpuData;

import java.util.List;
import java.util.UUID;

public interface CpuService {
    CpuEntity addCpu(CpuData data);

    CpuEntity getCpuById(UUID productId);

    void validateProcessorModels(List<String> cpuModels);
}