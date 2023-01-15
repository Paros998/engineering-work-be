package pg.search.store.infrastructure.product.pc;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.pc.PcData;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.product.cpu.CpuService;
import pg.search.store.infrastructure.product.exception.ProductAlreadyExistsException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PcServiceImpl implements PcService {
    private final PcRepository pcRepository;
    private final CpuService cpuService;
    private final CardService cardService;

    public PcEntity getPcById(final UUID productId) {
        return pcRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId, PcEntity.class));
    }

    public PcEntity addPc(final PcData data) {
        final var pcEntity = toPcEntity(data);

        pcRepository.save(pcEntity);

        return pcEntity;
    }

    public PcEntity toPcEntity(final PcData data) {
        if (data.getTitle() != null && pcRepository.existsByTitle(data.getTitle())) {
            throw new ProductAlreadyExistsException(data.getTitle());
        }

        PcEntity pcEntity = new PcEntity();

        return pcEntity
                .setProductType(PcData.productType)
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setDateOfProduction(CommonData.parseFrom(data.getDateOfProduction()))

                .setCpu(cpuService.getCpuById(data.getCpu().getProductId()))
                .setGpuCard(cardService.getCardById(data.getGpuCard().getProductId()))

                .setChipset(data.getChipset())
                .setAdditionalAccessories(data.getAdditionalAccessories())
                .setConnectivity(data.getConnectivity())
                .setDepth(data.getDepth())
                .setDiskDrive(data.getDiskDrive())
                .setFreeInternalPorts(data.getFreeInternalPorts())
                .setPsu(data.getPsu())
                .setPsuPower(data.getPsuPower())
                .setFrontPanelConnectors(data.getFrontPanelConnectors())
                .setHddDrives(data.getHddDrives())
                .setHeight(data.getHeight())
                .setSound(data.getSound())
                .setWidth(data.getWidth())
                .setBackPanelConnectors(data.getBackPanelConnectors())
                .setRamAmount(data.getRamAmount())
                .setRamClock(data.getRamClock())
                .setRamType(data.getRamType())
                .setSsdDrives(data.getSsdDrives())
                .setPsuEfficiency(data.getPsuEfficiency())
                .setSystem(data.getSystem());
    }
}