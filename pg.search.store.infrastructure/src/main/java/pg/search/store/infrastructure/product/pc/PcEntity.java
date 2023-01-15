package pg.search.store.infrastructure.product.pc;

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
@Table(name = "computers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PcEntity extends ProductEntity {

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
    private Map<String, Integer> backPanelConnectors;

    @ElementCollection
    private Map<String, Integer> frontPanelConnectors;

    @ElementCollection
    private Map<String, Integer> freeInternalPorts;

    private String psu;

    private Integer psuPower;

    private String psuEfficiency;

    @ElementCollection
    private Map<String, Integer> additionalAccessories;

    @Enumerated(EnumType.STRING)
    private OperatingSystem system;

    private String height;
    private String width;
    private String depth;

    private Float totalSpaceAvailable;

    @PrePersist
    @PreUpdate
    void calculateFreeSpace() {
        totalSpaceAvailable = 0.0f;
        hddDrives.forEach((disk, space) -> totalSpaceAvailable += space);
        ssdDrives.forEach((disk, space) -> totalSpaceAvailable += space);
    }

    @Override
    public Double getPeakPerformance(Double base) {
        return ((cpu.getBoostPerformance() + gpuCard.getBoostProcessingPower()) / 2) / base * 100;
    }

    @Override
    public Double getAvgPerformance(Double base) {
        return ((cpu.getBasePerformance() + gpuCard.getProcessingPower()) / 2) / base * 100;
    }

    @Override
    public PcEntity setProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public PcEntity setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public PcEntity setProducentCode(final String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    @Override
    public PcEntity setProductPhoto(final FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    @Override
    public PcEntity setProducentSite(final String producentSite) {
        this.producentSite = producentSite;
        return this;
    }

    @Override
    public PcEntity setDateOfProduction(final LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
        return this;
    }

    public PcEntity setGpuCard(final CardEntity gpuCard) {
        this.gpuCard = gpuCard;
        return this;
    }

    public PcEntity setCpu(final CpuEntity cpu) {
        this.cpu = cpu;
        return this;
    }

    public PcEntity setRamAmount(final Float ramAmount) {
        this.ramAmount = ramAmount;
        return this;
    }

    public PcEntity setRamType(final RamType ramType) {
        this.ramType = ramType;
        return this;
    }

    public PcEntity setRamClock(final Float ramClock) {
        this.ramClock = ramClock;
        return this;
    }

    public PcEntity setHddDrives(Map<String, Integer> hddDrives) {
        this.hddDrives = hddDrives;
        return this;
    }

    public PcEntity setSsdDrives(Map<String, Integer> ssdDrives) {
        this.ssdDrives = ssdDrives;
        return this;
    }

    public PcEntity setChipset(final String chipset) {
        this.chipset = chipset;
        return this;
    }

    public PcEntity setDiskDrive(final Boolean diskDrive) {
        this.diskDrive = diskDrive;
        return this;
    }

    public PcEntity setSound(final String sound) {
        this.sound = sound;
        return this;
    }

    public PcEntity setConnectivity(final List<String> connectivity) {
        this.connectivity = connectivity;
        return this;
    }

    public PcEntity setBackPanelConnectors(final Map<String, Integer> backPanelConnectors) {
        this.backPanelConnectors = backPanelConnectors;
        return this;
    }

    public PcEntity setFrontPanelConnectors(final Map<String, Integer> frontPanelConnectors) {
        this.frontPanelConnectors = frontPanelConnectors;
        return this;
    }

    public PcEntity setFreeInternalPorts(final Map<String, Integer> freeInternalPorts) {
        this.freeInternalPorts = freeInternalPorts;
        return this;
    }

    public PcEntity setPsu(final String psu) {
        this.psu = psu;
        return this;
    }

    public PcEntity setPsuPower(final Integer psuPower) {
        this.psuPower = psuPower;
        return this;
    }

    public PcEntity setPsuEfficiency(final String psuEfficiency) {
        this.psuEfficiency = psuEfficiency;
        return this;
    }

    public PcEntity setAdditionalAccessories(final Map<String, Integer> additionalAccessories) {
        this.additionalAccessories = additionalAccessories;
        return this;
    }

    public PcEntity setSystem(final OperatingSystem system) {
        this.system = system;
        return this;
    }

    public PcEntity setHeight(final String height) {
        this.height = height;
        return this;
    }

    public PcEntity setWidth(final String width) {
        this.width = width;
        return this;
    }

    public PcEntity setDepth(final String depth) {
        this.depth = depth;
        return this;
    }
}