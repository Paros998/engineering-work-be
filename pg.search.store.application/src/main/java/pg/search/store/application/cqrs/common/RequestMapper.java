package pg.search.store.application.cqrs.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import pg.search.store.application.cqrs.game.command.AddGameCommand;
import pg.search.store.application.cqrs.product.command.card.AbstractCardCommand;
import pg.search.store.application.cqrs.product.command.console.AddConsoleCommand;
import pg.search.store.application.cqrs.product.command.cpu.AddCpuCommand;
import pg.search.store.application.cqrs.product.command.laptop.AddLaptopCommand;
import pg.search.store.application.cqrs.product.command.pc.AddPcCommand;
import pg.search.store.application.cqrs.store.command.AddStoreCommand;
import pg.search.store.application.cqrs.store.offer.command.AddOfferCommand;
import pg.search.store.application.cqrs.user.command.CreateUserCommand;
import pg.search.store.domain.game.GameData;
import pg.search.store.domain.game.Platform;
import pg.search.store.domain.product.Console;
import pg.search.store.domain.product.GpuData;
import pg.search.store.domain.product.ProcessorData;
import pg.search.store.domain.product.RamType;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.domain.product.card.MemoryType;
import pg.search.store.domain.product.card.PciType;
import pg.search.store.domain.product.card.Technology;
import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.domain.product.console.ConsoleProducer;
import pg.search.store.domain.product.console.CpuProducer;
import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.domain.product.pc.PcData;
import pg.search.store.domain.store.OfferData;
import pg.search.store.domain.store.StoreData;
import pg.search.store.domain.system.OperatingSystem;
import pg.search.store.domain.user.UserCredentialsData;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RequestMapper {
    public UserCredentialsData toUserCredentialsData(final @NonNull CreateUserCommand command) {
        return UserCredentialsData.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .role(command.getRole())
                .username(command.getUsername())
                .build();
    }

    public CardData toCardData(final @NonNull AbstractCardCommand command) {
        if (!Technology.isValidTechnology(command.getTechnology())) {
            command.setTechnology(Technology.NVIDIA.name());
        }

        if (!MemoryType.isValidType(command.getTypeOfMemory())) {
            command.setTypeOfMemory(MemoryType.GDDR5.name());
        }

        if (!PciType.isValidType(command.getTypeOfPciConnector())) {
            command.setTypeOfPciConnector(PciType.PCI_4X16.name());
        }

        return CardData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .dateOfProduction(command.getDateOfProduction())
                .cardModel(command.getCardModel())
                .recommendedPower(command.getRecommendedPower())
                .rtxSupport(command.getRtxSupport())
                .supportedLibraries(command.getSupportedLibraries())
                .cudaCoresAmount(command.getCudaCoresAmount())
                .powerConsumption(command.getPowerConsumption())
                .powerConnector(command.getPowerConnector())
                .boostCoreClock(command.getBoostCoreClock())
                .cooling(command.getCooling())
                .coreClock(command.getCoreClock())
                .recommendedPower(command.getRecommendedPower())
                .memoryAmount(command.getMemoryAmount())
                .supportedDirectX(command.getSupportedDirectX())
                .memoryClock(command.getMemoryClock())
                .memoryBus(command.getMemoryBus())
                .technology(Technology.valueOf(command.getTechnology()))
                .typeOfMemory(MemoryType.valueOf(command.getTypeOfMemory()))
                .typeOfPciConnector(PciType.valueOf(command.getTypeOfPciConnector()))
                .maxNumberOfUnitsInSLI(command.getMaxNumberOfUnitsInSLI() >= 1 ? command.getMaxNumberOfUnitsInSLI() : 1)
                .build();
    }

    public CpuData toCpuData(final AddCpuCommand command) {
        if (!CpuProducer.isValidType(command.getProducer())) {
            command.setProducer(CpuProducer.INTEL.name());
        }

        return CpuData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .dateOfProduction(command.getDateOfProduction())
                .cpuModel(command.getCpuModel())
                .producer(CpuProducer.valueOf(command.getProducer()))
                .maxTdp(command.getMaxTdp())
                .technology(command.getTechnology())
                .coreClock(command.getCoreClock())
                .boostCoreClock(command.getBoostCoreClock())
                .totalSpecification(command.getTotalSpecification())
                .socket(command.getSocket())
                .series(command.getSeries())
                .version(command.getVersion())
                .cores(command.getCores())
                .threads(command.getThreads())
                .instructionsPerCycle(command.getInstructionsPerCycle())
                .build();
    }

    public ConsoleData toConsoleData(final AddConsoleCommand command) {
        if (!Platform.isValidType(command.getPlatform())) {
            command.setPlatform(Platform.PS4.name());
        }

        if (!Console.isValidType(command.getConsole())) {
            command.setConsole(Console.PS4.name());
        }

        if (!ConsoleProducer.isValidType(command.getProducer())) {
            command.setProducer(ConsoleProducer.SONY.name());
        }

        return ConsoleData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .dateOfProduction(command.getDateOfProduction())
                .platform(Platform.valueOf(command.getPlatform()))
                .console(Console.valueOf(command.getConsole()))
                .producer(ConsoleProducer.valueOf(command.getProducer()))
                .build();
    }

    public StoreData toStoreData(final AddStoreCommand command) {
        return StoreData.builder()
                .name(command.getName())
                .storePhotoId(command.getStorePhotoId())
                .website(command.getWebsite())
                .ratingScore(command.getRatingScore())
                .phone(command.getPhone())
                .offersIds(command.getOffersIds())
                .build();
    }

    public OfferData toOfferData(final AddOfferCommand command) {
        return OfferData.builder()
                .hasFreeShipping(command.getHasFreeShipping())
                .currency(command.getCurrency())
                .offerWebsite(command.getOfferWebsite())
                .price(command.getPrice())
                .productId(command.getProductId())
                .storeId(command.getStoreId())
                .build();
    }

    public GameData toGameData(final AddGameCommand command) {
        List<GameData.PlatformScore> scoreList = new ArrayList<>();
        List<Platform> platforms = new ArrayList<>();
        List<OperatingSystem> supportedSystems = new ArrayList<>();

        if (command.getScoreOnPlatforms() != null)
            command.getScoreOnPlatforms().forEach((platform, score) -> {
                if (Platform.isValidType(platform))
                    scoreList.add(GameData.PlatformScore.builder().platform(Platform.valueOf(platform)).score(score).build());
            });

        if (command.getPlatforms() != null)
            command.getPlatforms().forEach(platform -> {
                if (Platform.isValidType(platform))
                    platforms.add(Platform.valueOf(platform));
            });

        if (command.getSupportedSystems() != null)
            command.getSupportedSystems().forEach(system -> {
                if (OperatingSystem.isValidType(system))
                    supportedSystems.add(OperatingSystem.valueOf(system));
            });

        return GameData.builder()
                .title(command.getTitle())
                .releaseDate(command.getReleaseDate())
                .description(command.getDescription())
                .scoreOnPlatforms(scoreList)
                .platforms(platforms)
                .file(command.getFile())
                .graphicRequirements(command.getGraphicRequirements())
                .processorRequirements(command.getProcessorRequirements())
                .requiredGraphicCardModels(command.getRequiredGraphicCardModels())
                .requiredProcessorModels(command.getRequiredProcessorModels())
                .requiredRam(command.getRequiredRam())
                .supportedSystems(supportedSystems)
                .requiredSpace(command.getRequiredSpace())
                .build();
    }

    public PcData toPcData(final AddPcCommand command, final GpuData gpu, final ProcessorData cpu) {
        if (!RamType.isValidType(command.getRamType())) {
            command.setRamType(RamType.DDR4.name());
        }

        if (!OperatingSystem.isValidType(command.getSystem())) {
            command.setSystem(OperatingSystem.WIN_10.name());
        }

        return PcData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .dateOfProduction(command.getDateOfProduction())

                .gpuCard(gpu)
                .cpu(cpu)

                .chipset(command.getChipset())
                .additionalAccessories(command.getAdditionalAccessories())
                .connectivity(command.getConnectivity())
                .depth(command.getDepth())
                .diskDrive(command.getDiskDrive())
                .freeInternalPorts(command.getFreeInternalPorts())
                .psu(command.getPsu())
                .psuPower(command.getPsuPower())
                .frontPanelConnectors(command.getFrontPanelConnectors())
                .hddDrives(command.getHddDrives())
                .height(command.getHeight())
                .sound(command.getSound())
                .width(command.getWidth())
                .backPanelConnectors(command.getBackPanelConnectors())
                .ramAmount(command.getRamAmount())
                .ramClock(command.getRamClock())
                .ramType(RamType.valueOf(command.getRamType()))
                .ssdDrives(command.getSsdDrives())
                .psuEfficiency(command.getPsuEfficiency())
                .system(OperatingSystem.valueOf(command.getSystem()))

                .build();
    }

    public LaptopData toLaptopData(final AddLaptopCommand command, final GpuData gpu, final ProcessorData cpu) {
        if (!RamType.isValidType(command.getRamType())) {
            command.setRamType(RamType.DDR4.name());
        }

        if (!OperatingSystem.isValidType(command.getSystem())) {
            command.setSystem(OperatingSystem.WIN_10.name());
        }

        return LaptopData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .dateOfProduction(command.getDateOfProduction())

                .gpuCard(gpu)
                .cpu(cpu)

                .ramAmount(command.getRamAmount())
                .ramType(RamType.valueOf(command.getRamType()))
                .ramClock(command.getRamClock())
                .hddDrives(command.getHddDrives())
                .ssdDrives(command.getSsdDrives())
                .chipset(command.getChipset())
                .diskDrive(command.getDiskDrive())
                .sound(command.getSound())
                .connectivity(command.getConnectivity())
                .leftPanelConnectors(command.getLeftPanelConnectors())
                .rightPanelConnectors(command.getRightPanelConnectors())
                .psu(command.getPsu())
                .psuPower(command.getPsuPower())
                .psuEfficiency(command.getPsuEfficiency())
                .maxTDP(command.getMaxTDP())
                .additionalAccessories(command.getAdditionalAccessories())
                .system(OperatingSystem.valueOf(command.getSystem()))
                .height(command.getHeight())
                .width(command.getWidth())
                .depth(command.getDepth())

                .touchableScreen(command.getTouchableScreen())
                .screenType(command.getScreenType())
                .screenSize(command.getScreenSize())
                .screenResolution(command.getScreenResolution())
                .screenRefreshRate(command.getScreenRefreshRate())
                .matrixLightness(command.getMatrixLightness())
                .batteryType(command.getBatteryType())
                .batteryCapacity(command.getBatteryCapacity())
                .fingerPrintReader(command.getFingerPrintReader())
                .weight(command.getWeight())
                .build();
    }

}