package pg.search.store.infrastructure.product.cpu;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.product.exception.ProductAlreadyExistsException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CpuServiceImpl implements CpuService {
    private final CpuRepository cpuRepository;

    public CpuEntity addCpu(final CpuData data) {
        final var cpuEntity = toCpuEntity(data);

        cpuRepository.save(cpuEntity);

        return cpuEntity;
    }

    public CpuEntity getCpuById(final UUID productId) {
        return cpuRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId, CpuEntity.class));
    }

    public void validateProcessorModels(final List<String> cpuModels) {
        cpuModels.forEach(model -> {
            final var results = cpuRepository.findMatchesByModel(Collections.singleton(model));
            if (results.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Required Cpu Model {%s} that is present on " +
                        "game you're adding has not equivalents in Processor Present in Database", model));
        });
    }

    public CpuEntity toCpuEntity(final CpuData data) {
        if (data.getTitle() != null && cpuRepository.existsByTitle(data.getTitle())) {
            throw new ProductAlreadyExistsException(data.getTitle());
        }

        CpuEntity cpuEntity = new CpuEntity();

        return cpuEntity
                .setProductType(CpuData.productType)
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setDateOfProduction(CommonData.parseFrom(data.getDateOfProduction()))
                .setCpuModel(data.getCpuModel())
                .setProducer(data.getProducer())
                .setMaxTdp(data.getMaxTdp())
                .setTechnology(data.getTechnology())
                .setCoreClock(data.getCoreClock())
                .setBoostCoreClock(data.getBoostCoreClock())
                .setTotalSpecification(data.getTotalSpecification())
                .setSocket(data.getSocket())
                .setSeries(data.getSeries())
                .setVersion(data.getVersion())
                .setCores(data.getCores())
                .setThreads(data.getThreads())
                .setInstructionsPerCycle(data.getInstructionsPerCycle())
                .setOnlyLaptopCpu(data.isOnlyLaptopCpu());

    }
}