package pg.search.store.domain.product.laptop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import pg.search.store.domain.product.GpuData;
import pg.search.store.domain.product.ProcessorData;
import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.RamType;
import pg.search.store.domain.system.OperatingSystem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.laptop.LaptopEntity} entity
 */
@AllArgsConstructor
@Builder
@Getter
public class LaptopData implements Serializable {
    public static final ProductType productType = ProductType.LAPTOP;

    private final UUID productId;
    private final String title;
    private final String producentCode;
    private final String producentSite;
    private final String dateOfProduction;
    private final String productPhoto;

    private final GpuData gpuCard;
    private final ProcessorData cpu;

    private final Float ramAmount;
    private final RamType ramType;
    private final Float ramClock;
    private final Map<String, Integer> hddDrives;
    private final Map<String, Integer> ssdDrives;
    private final String chipset;
    private final Boolean diskDrive;
    private final String sound;
    private final List<String> connectivity;
    private final Map<String, Integer> leftPanelConnectors;
    private final Map<String, Integer> rightPanelConnectors;
    private final String psu;
    private final Integer psuPower;
    private final String psuEfficiency;
    private final Integer maxTDP;
    private final Map<String, Integer> additionalAccessories;
    private final OperatingSystem system;
    private final String height;
    private final String width;
    private final String depth;
    private final Boolean touchableScreen;
    private final String screenType;
    private final String screenSize;
    private final String screenResolution;
    private final Integer screenRefreshRate;
    private final String matrixLightness;
    private final String batteryType;
    private final String batteryCapacity;
    private final Boolean fingerPrintReader;
    private final String weight;
}