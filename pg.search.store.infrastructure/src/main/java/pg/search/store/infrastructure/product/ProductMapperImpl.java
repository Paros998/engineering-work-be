package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.stereotype.Service;

import pg.search.store.domain.product.BasicProduct;
import pg.search.store.domain.product.BasicProductWithPerformance;
import pg.search.store.domain.product.GpuData;
import pg.search.store.domain.product.ProcessorData;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.domain.product.pc.PcData;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.console.ConsoleEntity;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.laptop.LaptopEntity;
import pg.search.store.infrastructure.product.pc.PcEntity;
import pg.search.store.infrastructure.store.offer.OfferEntity;
import pg.search.store.infrastructure.store.offer.OfferRepository;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserRepository;
import pg.search.store.infrastructure.user.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final OfferRepository offerRepository;
    private final ProductService productService;
    private final UserService userService;

    private final CurrencyProvider currencyProvider;
    private final UserRepository userRepository;

    public BasicProduct toBasicProduct(final @NonNull ProductEntity product, final UUID userId) {
        final UUID productId = product.getProductId();
        final List<OfferEntity> offers = offerRepository.findByProductProductId(productId);

        Optional<UserEntity> user = userId == null ? Optional.empty() : userRepository.findById(userId);
        String userCurrency = user.isPresent() ? user.get().getCurrency() : CurrencyProvider.DEFAULT_CURRENCY;

        final Double lowestPrice = offers.stream()
                .min(Comparator.comparingDouble(OfferEntity::getPrice))
                .map(entity -> currencyProvider.exchangePrice(entity.getCurrency(), userCurrency, entity.getPrice()))
                .orElse(0d);

        return new BasicProduct()
                .setProductId(productId)
                .setTitle(product.getTitle())
                .setProductType(product.productType)
                .setAvailable(!offers.isEmpty())
                .setStoresNumber(offers.size())
                .setStoresLowestPrice(String.format("%.2f", lowestPrice))
                .setProductPhoto(productService.getProductPhoto(product))
                .setFollowed(userService.isUserFollowingProduct(userId, productId))
                .setMarked(userService.hasUserMarkedProduct(userId, productId));
    }

    public BasicProductWithPerformance toBasicProductWithPerformance(final @NonNull ProductEntity product, final UUID userId,
                                                                     final Double peakPerformance, final Double baseAvgPerformance) {
        final var basicProduct = toBasicProduct(product, userId);

        return new BasicProductWithPerformance()
                .setProductId(basicProduct.getProductId())
                .setTitle(basicProduct.getTitle())
                .setProductType(basicProduct.getProductType())
                .setAvailable(basicProduct.getAvailable())
                .setStoresNumber(basicProduct.getStoresNumber())
                .setStoresLowestPrice(basicProduct.getStoresLowestPrice())
                .setProductPhoto(basicProduct.getProductPhoto())
                .setFollowed(basicProduct.getFollowed())
                .setMarked(basicProduct.getMarked())
                .setAvgPerformance(product.getAvgPerformance(baseAvgPerformance))
                .setPeakPerformance(product.getPeakPerformance(peakPerformance));
    }

    public CardData toCardData(final CardEntity card) {
        return CardData.builder()
                .title(card.getTitle())
                .producentCode(card.getProducentCode())
                .producentSite(card.getProducentSite())
                .dateOfProduction(card.getDateOfProduction().toString())
                .productPhoto(productService.getProductPhoto(card))
                .cardModel(card.getCardModel())
                .technology(card.getTechnology())
                .rtxSupport(card.getRtxSupport())
                .supportedLibraries(card.getSupportedLibraries())
                .cudaCoresAmount(card.getCudaCoresAmount())
                .powerConsumption(card.getPowerConsumption())
                .recommendedPower(card.getRecommendedPower())
                .cooling(card.getCooling())
                .powerConnector(card.getPowerConnector())
                .coreClock(card.getCoreClock())
                .boostCoreClock(card.getBoostCoreClock())
                .memoryAmount(card.getMemoryAmount())
                .supportedDirectX(card.getSupportedDirectX())
                .typeOfMemory(card.getTypeOfMemory())
                .typeOfPciConnector(card.getTypeOfPciConnector())
                .memoryClock(card.getMemoryClock())
                .memoryBus(card.getMemoryBus())
                .maxNumberOfUnitsInSLI(card.getMaxNumberOfUnitsInSLI())
                .build();
    }

    public CpuData toCpuData(final CpuEntity cpu) {
        return CpuData.builder()
                .title(cpu.getTitle())
                .producentCode(cpu.getProducentCode())
                .producentSite(cpu.getProducentSite())
                .dateOfProduction(cpu.getDateOfProduction().toString())
                .productPhoto(productService.getProductPhoto(cpu))
                .cpuModel(cpu.getCpuModel())
                .producer(cpu.getProducer())
                .maxTdp(cpu.getMaxTdp())
                .technology(cpu.getTechnology())
                .coreClock(cpu.getCoreClock())
                .boostCoreClock(cpu.getBoostCoreClock())
                .totalSpecification(cpu.getTotalSpecification())
                .socket(cpu.getSocket())
                .series(cpu.getSeries())
                .version(cpu.getVersion())
                .cores(cpu.getCores())
                .threads(cpu.getThreads())
                .instructionsPerCycle(cpu.getInstructionsPerCycle())
                .build();
    }

    public ConsoleData toConsoleData(final ConsoleEntity console) {
        return ConsoleData.builder()
                .title(console.getTitle())
                .producentCode(console.getProducentCode())
                .producentSite(console.getProducentSite())
                .dateOfProduction(console.getDateOfProduction().toString())
                .productPhoto(productService.getProductPhoto(console))
                .platform(console.getPlatform())
                .console(console.getConsole())
                .producer(console.getProducer())
                .build();
    }

    public PcData toPcData(final PcEntity pc) {
        CardEntity cardEntity = pc.getGpuCard();
        CpuEntity cpuEntity = pc.getCpu();

        GpuData gpu = GpuData.builder()
                .productId(cardEntity.getProductId())
                .title(cardEntity.getTitle())
                .productPhoto(productService.getProductPhoto(cardEntity))
                .build();

        ProcessorData cpu = ProcessorData.builder()
                .productId(cpuEntity.getProductId())
                .producer(cpuEntity.getProducer())
                .title(cpuEntity.getTitle())
                .productPhoto(productService.getProductPhoto(cpuEntity))
                .build();

        return PcData.builder()
                .title(pc.getTitle())
                .producentCode(pc.getProducentCode())
                .producentSite(pc.getProducentSite())
                .dateOfProduction(pc.getDateOfProduction().toString())
                .productPhoto(productService.getProductPhoto(pc))

                .gpuCard(gpu)
                .cpu(cpu)

                .chipset(pc.getChipset())
                .additionalAccessories(pc.getAdditionalAccessories())
                .connectivity(pc.getConnectivity())
                .depth(pc.getDepth())
                .diskDrive(pc.getDiskDrive())
                .freeInternalPorts(pc.getFreeInternalPorts())
                .psu(pc.getPsu())
                .psuPower(pc.getPsuPower())
                .frontPanelConnectors(pc.getFrontPanelConnectors())
                .hddDrives(pc.getHddDrives())
                .height(pc.getHeight())
                .sound(pc.getSound())
                .width(pc.getWidth())
                .backPanelConnectors(pc.getBackPanelConnectors())
                .ramAmount(pc.getRamAmount())
                .ramClock(pc.getRamClock())
                .ramType(pc.getRamType())
                .ssdDrives(pc.getSsdDrives())
                .psuEfficiency(pc.getPsuEfficiency())
                .system(pc.getSystem())

                .build();
    }

    public LaptopData toLaptopData(final LaptopEntity laptop) {
        CardEntity cardEntity = laptop.getGpuCard();
        CpuEntity cpuEntity = laptop.getCpu();

        GpuData gpu = GpuData.builder()
                .productId(cardEntity.getProductId())
                .title(cardEntity.getTitle())
                .productPhoto(productService.getProductPhoto(cardEntity))
                .build();

        ProcessorData cpu = ProcessorData.builder()
                .productId(cpuEntity.getProductId())
                .producer(cpuEntity.getProducer())
                .title(cpuEntity.getTitle())
                .productPhoto(productService.getProductPhoto(cpuEntity))
                .build();

        return LaptopData.builder()
                .title(laptop.getTitle())
                .producentCode(laptop.getProducentCode())
                .producentSite(laptop.getProducentSite())
                .dateOfProduction(laptop.getDateOfProduction().toString())
                .productPhoto(productService.getProductPhoto(laptop))

                .gpuCard(gpu)
                .cpu(cpu)

                .ramAmount(laptop.getRamAmount())
                .ramType(laptop.getRamType())
                .ramClock(laptop.getRamClock())
                .hddDrives(laptop.getHddDrives())
                .ssdDrives(laptop.getSsdDrives())
                .chipset(laptop.getChipset())
                .diskDrive(laptop.getDiskDrive())
                .sound(laptop.getSound())
                .connectivity(laptop.getConnectivity())
                .leftPanelConnectors(laptop.getLeftPanelConnectors())
                .rightPanelConnectors(laptop.getRightPanelConnectors())
                .psu(laptop.getPsu())
                .psuPower(laptop.getPsuPower())
                .psuEfficiency(laptop.getPsuEfficiency())
                .maxTDP(laptop.getMaxTDP())
                .additionalAccessories(laptop.getAdditionalAccessories())
                .system(laptop.getSystem())
                .height(laptop.getHeight())
                .width(laptop.getWidth())
                .depth(laptop.getDepth())

                .touchableScreen(laptop.getTouchableScreen())
                .screenType(laptop.getScreenType())
                .screenSize(laptop.getScreenSize())
                .screenResolution(laptop.getScreenResolution())
                .screenRefreshRate(laptop.getScreenRefreshRate())
                .matrixLightness(laptop.getMatrixLightness())
                .batteryType(laptop.getBatteryType())
                .batteryCapacity(laptop.getBatteryCapacity())
                .fingerPrintReader(laptop.getFingerPrintReader())
                .weight(laptop.getWeight())

                .build();
    }

    public GpuData toGpuData(final CardEntity card) {
        return GpuData.builder()
                .title(card.getTitle())
                .productId(card.getProductId())
                .productPhoto(productService.getProductPhoto(card))
                .build();
    }

    public ProcessorData toProcessorData(final CpuEntity cpu) {
        return ProcessorData.builder()
                .title(cpu.getTitle())
                .productId(cpu.getProductId())
                .productPhoto(productService.getProductPhoto(cpu))
                .producer(cpu.getProducer())
                .build();
    }
}