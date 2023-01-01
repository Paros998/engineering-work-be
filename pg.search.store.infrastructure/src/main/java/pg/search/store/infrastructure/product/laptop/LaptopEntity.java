package pg.search.store.infrastructure.product.laptop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.RamType;
import pg.search.store.domain.system.OperatingSystem;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.cpu.CpuEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "laptops")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LaptopEntity extends ProductEntity {

    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonBackReference
    private CardEntity gpuCard;

    @ManyToOne
    @JoinColumn(name = "processor_id")
    @JsonBackReference
    private CpuEntity cpu;

    private Float ramAmount;

    @Enumerated(EnumType.STRING)
    private RamType ramType;

    private Float ramClock;

    @ElementCollection
    private Map<String, Integer> hddDrives;

    @ElementCollection
    private Map<String, Integer> ssdDrives;

    private String chipset;

    private Boolean diskDrive;

    private String sound;

    @ElementCollection(targetClass = String.class)
    private List<String> connectivity;

    @ElementCollection
    private Map<String, Integer> leftPanelConnectors;

    @ElementCollection
    private Map<String, Integer> rightPanelConnectors;

    private String psu;

    private Integer psuPower;

    private String psuEfficiency;

    private Integer maxTDP;

    @ElementCollection
    private Map<String, Integer> additionalAccessories;

    @Enumerated(EnumType.STRING)
    private OperatingSystem system;

    private String height;
    private String width;
    private String depth;

    private Boolean touchableScreen;

    private String screenType;

    private String screenSize;

    private String screenResolution;

    private Integer screenRefreshRate;

    private String matrixLightness;

    private String batteryType;

    private String batteryCapacity;

    private Boolean fingerPrintReader;

    private String weight;

    @Override
    public Double getPeakPerformance(Double base) {
        return ((cpu.getBoostPerformance() + gpuCard.getBoostProcessingPower()) / 2) / base * 100;
    }

    @Override
    public Double getAvgPerformance(Double base) {
        return ((cpu.getBasePerformance() + gpuCard.getProcessingPower()) / 2) / base * 100;
    }

    @Override
    public LaptopEntity setProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public LaptopEntity setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public LaptopEntity setProducentCode(final String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    @Override
    public LaptopEntity setProductPhoto(final FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    @Override
    public LaptopEntity setProducentSite(final String producentSite) {
        this.producentSite = producentSite;
        return this;
    }

    @Override
    public LaptopEntity setDateOfProduction(final LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
        return this;
    }

    public LaptopEntity setGpuCard(final CardEntity gpuCard) {
        this.gpuCard = gpuCard;
        return this;
    }

    public LaptopEntity setCpu(final CpuEntity cpu) {
        this.cpu = cpu;
        return this;
    }

    public LaptopEntity setRamAmount(final Float ramAmount) {
        this.ramAmount = ramAmount;
        return this;
    }

    public LaptopEntity setRamType(final RamType ramType) {
        this.ramType = ramType;
        return this;
    }

    public LaptopEntity setRamClock(final Float ramClock) {
        this.ramClock = ramClock;
        return this;
    }

    public LaptopEntity setHddDrives(Map<String, Integer> hddDrives) {
        this.hddDrives = hddDrives;
        return this;
    }

    public LaptopEntity setSsdDrives(Map<String, Integer> ssdDrives) {
        this.ssdDrives = ssdDrives;
        return this;
    }

    public LaptopEntity setChipset(final String chipset) {
        this.chipset = chipset;
        return this;
    }

    public LaptopEntity setDiskDrive(final Boolean diskDrive) {
        this.diskDrive = diskDrive;
        return this;
    }

    public LaptopEntity setSound(final String sound) {
        this.sound = sound;
        return this;
    }

    public LaptopEntity setConnectivity(final List<String> connectivity) {
        this.connectivity = connectivity;
        return this;
    }

    public LaptopEntity setLeftPanelConnectors(final Map<String, Integer> backPanelConnectors) {
        this.leftPanelConnectors = backPanelConnectors;
        return this;
    }

    public LaptopEntity setRightPanelConnectors(final Map<String, Integer> frontPanelConnectors) {
        this.rightPanelConnectors = frontPanelConnectors;
        return this;
    }

    public LaptopEntity setPsu(final String psu) {
        this.psu = psu;
        return this;
    }

    public LaptopEntity setPsuPower(final Integer psuPower) {
        this.psuPower = psuPower;
        return this;
    }

    public LaptopEntity setPsuEfficiency(final String psuEfficiency) {
        this.psuEfficiency = psuEfficiency;
        return this;
    }

    public LaptopEntity setAdditionalAccessories(final Map<String, Integer> additionalAccessories) {
        this.additionalAccessories = additionalAccessories;
        return this;
    }

    public LaptopEntity setSystem(final OperatingSystem system) {
        this.system = system;
        return this;
    }

    public LaptopEntity setHeight(final String height) {
        this.height = height;
        return this;
    }

    public LaptopEntity setWidth(final String width) {
        this.width = width;
        return this;
    }

    public LaptopEntity setDepth(final String depth) {
        this.depth = depth;
        return this;
    }

    public LaptopEntity setMaxTDP(final Integer maxTDP) {
        this.maxTDP = maxTDP;
        return this;
    }

    public LaptopEntity setTouchableScreen(final Boolean touchableScreen) {
        this.touchableScreen = touchableScreen;
        return this;
    }

    public LaptopEntity setScreenType(final String screenType) {
        this.screenType = screenType;
        return this;
    }

    public LaptopEntity setScreenSize(final String screenSize) {
        this.screenSize = screenSize;
        return this;
    }

    public LaptopEntity setScreenResolution(final String screenResolution) {
        this.screenResolution = screenResolution;
        return this;
    }

    public LaptopEntity setScreenRefreshRate(final Integer screenRefreshRate) {
        this.screenRefreshRate = screenRefreshRate;
        return this;
    }

    public LaptopEntity setMatrixLightness(final String matrixLightness) {
        this.matrixLightness = matrixLightness;
        return this;
    }

    public LaptopEntity setBatteryType(final String batteryType) {
        this.batteryType = batteryType;
        return this;
    }

    public LaptopEntity setBatteryCapacity(final String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
        return this;
    }

    public LaptopEntity setFingerPrintReader(final Boolean fingerPrintReader) {
        this.fingerPrintReader = fingerPrintReader;
        return this;
    }

    public LaptopEntity setWeight(final String weight) {
        this.weight = weight;
        return this;
    }
}