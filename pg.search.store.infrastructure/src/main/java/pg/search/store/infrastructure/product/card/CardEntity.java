package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.card.MemoryType;
import pg.search.store.domain.product.card.PciType;
import pg.search.store.domain.product.card.Technology;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.laptop.LaptopEntity;
import pg.search.store.infrastructure.product.pc.PcEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CardEntity extends ProductEntity {
    private String cardModel;
    @Enumerated(EnumType.STRING)
    private Technology technology;

    private Boolean rtxSupport;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
    private List<String> supportedLibraries;

    private Integer cudaCoresAmount;

    private Integer powerConsumption;

    private Integer recommendedPower;

    private String cooling;

    private String powerConnector;

    private Float coreClock;

    private Float boostCoreClock;

    private Float memoryAmount;

    private Float supportedDirectX;

    @Enumerated(EnumType.STRING)
    private MemoryType typeOfMemory;

    @Enumerated(EnumType.STRING)
    private PciType typeOfPciConnector;

    private Float memoryClock;

    private Integer memoryBus;

    private double memoryBandwidth;

    private Double processingPower;

    private Double boostProcessingPower;

    private Integer maxNumberOfUnitsInSLI;

    private Float bitcoinHashRate;

    @OneToMany(mappedBy = "gpuCard")
    @JsonManagedReference
    private List<PcEntity> usedInPcEntities;

    @OneToMany(mappedBy = "gpuCard")
    @JsonManagedReference
    private List<LaptopEntity> usedInLaptopEntities;

    @PrePersist
    @PreUpdate
    void calculatePerformance() {
        if (memoryBus != null && memoryClock != null && typeOfMemory != null) {
            this.memoryBandwidth = calculateMemoryBandwidth();
        }
        if (coreClock != null) this.processingPower = calculateProcessingPower();
        if (boostCoreClock != null) this.boostProcessingPower = calculateBoostProcessingPower();
    }

    private double calculateMemoryBandwidth() {
        return memoryBus / 8.00 * memoryClock * typeOfMemory.getScale() / 1000.00;
    }

    private double calculateProcessingPower() {
        return calculateTFlops(coreClock);
    }

    private double calculateBoostProcessingPower() {
        return calculateTFlops(boostCoreClock);
    }

    private double calculateTFlops(Float clock) {
        return cudaCoresAmount * clock * 2;
    }

    @Override
    public CardEntity setProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public CardEntity setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public CardEntity setProducentCode(final String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    @Override
    public CardEntity setProductPhoto(final FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    @Override
    public CardEntity setProducentSite(final String producentSite) {
        this.producentSite = producentSite;
        return this;
    }

    @Override
    public CardEntity setDateOfProduction(final LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
        return this;
    }

    public CardEntity setCardModel(final String cardModel) {
        this.cardModel = cardModel;
        return this;
    }

    public CardEntity setTechnology(final Technology technology) {
        this.technology = technology;
        return this;
    }

    public CardEntity setRtxSupport(final Boolean rtxSupport) {
        this.rtxSupport = rtxSupport;
        return this;
    }

    public CardEntity setSupportedLibraries(final List<String> supportedLibraries) {
        this.supportedLibraries = supportedLibraries;
        return this;
    }

    public CardEntity setCudaCoresAmount(final Integer cudaCoresAmount) {
        this.cudaCoresAmount = cudaCoresAmount;
        return this;
    }

    public CardEntity setPowerConsumption(final Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
        return this;
    }

    public CardEntity setRecommendedPower(final Integer recommendedPower) {
        this.recommendedPower = recommendedPower;
        return this;
    }

    public CardEntity setCooling(final String cooling) {
        this.cooling = cooling;
        return this;
    }

    public CardEntity setPowerConnector(final String powerConnector) {
        this.powerConnector = powerConnector;
        return this;
    }

    public CardEntity setCoreClock(final Float coreClock) {
        this.coreClock = coreClock;
        return this;
    }

    public CardEntity setBoostCoreClock(final Float boostCoreClock) {
        this.boostCoreClock = boostCoreClock;
        return this;
    }

    public CardEntity setMemoryAmount(final Float memoryAmount) {
        this.memoryAmount = memoryAmount;
        return this;
    }

    public CardEntity setSupportedDirectX(final Float supportedDirectX) {
        this.supportedDirectX = supportedDirectX;
        return this;
    }

    public CardEntity setTypeOfMemory(final MemoryType typeOfMemory) {
        this.typeOfMemory = typeOfMemory;
        return this;
    }

    public CardEntity setTypeOfPciConnector(final PciType typeOfPciConnector) {
        this.typeOfPciConnector = typeOfPciConnector;
        return this;
    }

    public CardEntity setMemoryClock(final Float memoryClock) {
        this.memoryClock = memoryClock;
        return this;
    }

    public CardEntity setMemoryBus(final Integer memoryBus) {
        this.memoryBus = memoryBus;
        return this;
    }

    public CardEntity setMaxNumberOfUnitsInSLI(final Integer maxNumberOfUnitsInSLI) {
        this.maxNumberOfUnitsInSLI = maxNumberOfUnitsInSLI;
        return this;
    }

    public CardEntity setBitcoinHashRate(final Float bitcoinHashRate) {
        this.bitcoinHashRate = bitcoinHashRate;
        return this;
    }

    @Override
    public Double getPeakPerformance(final Double base) {
        if (boostProcessingPower != null)
            return (boostProcessingPower / base) * 100;
        return 0.0;
    }

    @Override
    public Double getAvgPerformance(final Double base) {
        if (processingPower != null)
            return (processingPower / base) * 100;
        return 0.0;
    }
}