package pg.search.store.infrastructure.product.laptop;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.product.cpu.CpuService;
import pg.search.store.infrastructure.product.exception.ProductAlreadyExistsException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final CpuService cpuService;
    private final CardService cardService;

    public LaptopEntity getLaptopById(final UUID productId) {
        return laptopRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(productId, LaptopEntity.class));
    }

    public LaptopEntity addLaptop(LaptopData data) {
        final var laptopEntity = toLaptopEntity(data);

        laptopRepository.save(laptopEntity);

        return laptopEntity;
    }

    public LaptopEntity toLaptopEntity(final LaptopData data) {
        if (data.getTitle() != null && laptopRepository.existsByTitle(data.getTitle())) {
            throw new ProductAlreadyExistsException(data.getTitle());
        }

        LaptopEntity laptopEntity = new LaptopEntity();

        return laptopEntity
                .setProductType(CpuData.productType)
                .setTitle(data.getTitle())
                .setProducentCode(data.getProducentCode())
                .setProducentSite(data.getProducentSite())
                .setDateOfProduction(CommonData.parseFrom(data.getDateOfProduction()))

                .setCpu(cpuService.getCpuById(data.getCpu().getProductId()))
                .setGpuCard(cardService.getCardById(data.getGpuCard().getProductId()))

                .setRamAmount(data.getRamAmount())
                .setRamType(data.getRamType())
                .setRamClock(data.getRamClock())
                .setHddDrives(data.getHddDrives())
                .setSsdDrives(data.getSsdDrives())
                .setChipset(data.getChipset())
                .setDiskDrive(data.getDiskDrive())
                .setSound(data.getSound())
                .setConnectivity(data.getConnectivity())
                .setLeftPanelConnectors(data.getLeftPanelConnectors())
                .setRightPanelConnectors(data.getRightPanelConnectors())
                .setPsu(data.getPsu())
                .setPsuPower(data.getPsuPower())
                .setPsuEfficiency(data.getPsuEfficiency())
                .setMaxTDP(data.getMaxTDP())
                .setAdditionalAccessories(data.getAdditionalAccessories())
                .setSystem(data.getSystem())
                .setHeight(data.getHeight())
                .setWidth(data.getWidth())
                .setDepth(data.getDepth())

                .setTouchableScreen(data.getTouchableScreen())
                .setScreenType(data.getScreenType())
                .setScreenSize(data.getScreenSize())
                .setScreenResolution(data.getScreenResolution())
                .setScreenRefreshRate(data.getScreenRefreshRate())
                .setMatrixLightness(data.getMatrixLightness())
                .setBatteryType(data.getBatteryType())
                .setBatteryCapacity(data.getBatteryCapacity())
                .setFingerPrintReader(data.getFingerPrintReader())
                .setWeight(data.getWeight());
    }
}