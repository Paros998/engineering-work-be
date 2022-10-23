package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.search.store.domain.product.card.MemoryType;
import pg.search.store.domain.product.card.PciType;
import pg.search.store.domain.product.card.Technology;
import pg.search.store.infrastructure.product.ProductEntity;

import javax.persistence.*;

import java.util.List;

@Entity
@SecondaryTable(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CardEntity extends ProductEntity {
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

    private Integer coreClock;

    private Integer boostCoreClock;

    private Float memoryAmount;

    private Float supportedDirectX;

    @Enumerated(EnumType.STRING)
    private MemoryType typeOfMemory;

    @Enumerated(EnumType.STRING)
    private PciType typeOfPciConnector;

    private Integer memoryClock;

    private Integer memoryBus;

    @Transient
    private double memoryBandwidth;
    @Transient
    private double processingPower;
    @Transient
    private double boostProcessingPower;

    public double getMemoryBandwidth() {
        return memoryBus / 8.00 * memoryClock * typeOfMemory.getScale() / 1000.00;
    }

    public double getProcessingPower() {
        return calculateTFlops(coreClock);
    }

    public double getBoostProcessingPower() {
        return calculateTFlops(boostCoreClock);
    }

    private double calculateTFlops(Integer clock) {
        return cudaCoresAmount * (clock / 1000.00) * 2;
    }

    public CardEntity setTechnology(Technology technology) {
        this.technology = technology;
        return this;
    }

    public CardEntity setRtxSupport(Boolean rtxSupport) {
        this.rtxSupport = rtxSupport;
        return this;
    }

    public CardEntity setSupportedLibraries(List<String> supportedLibraries) {
        this.supportedLibraries = supportedLibraries;
        return this;
    }

    public CardEntity setCudaCoresAmount(Integer cudaCoresAmount) {
        this.cudaCoresAmount = cudaCoresAmount;
        return this;
    }

    public CardEntity setPowerConsumption(Integer powerConsumption) {
        this.powerConsumption = powerConsumption;
        return this;
    }

    public CardEntity setRecommendedPower(Integer recommendedPower) {
        this.recommendedPower = recommendedPower;
        return this;
    }

    public CardEntity setCooling(String cooling) {
        this.cooling = cooling;
        return this;
    }

    public CardEntity setPowerConnector(String powerConnector) {
        this.powerConnector = powerConnector;
        return this;
    }

    public CardEntity setCoreClock(Integer coreClock) {
        this.coreClock = coreClock;
        return this;
    }

    public CardEntity setBoostCoreClock(Integer boostCoreClock) {
        this.boostCoreClock = boostCoreClock;
        return this;
    }

    public CardEntity setMemoryAmount(Float memoryAmount) {
        this.memoryAmount = memoryAmount;
        return this;
    }

    public CardEntity setSupportedDirectX(Float supportedDirectX) {
        this.supportedDirectX = supportedDirectX;
        return this;
    }

    public CardEntity setTypeOfMemory(MemoryType typeOfMemory) {
        this.typeOfMemory = typeOfMemory;
        return this;
    }

    public CardEntity setTypeOfPciConnector(PciType typeOfPciConnector) {
        this.typeOfPciConnector = typeOfPciConnector;
        return this;
    }

    public CardEntity setMemoryClock(Integer memoryClock) {
        this.memoryClock = memoryClock;
        return this;
    }

    public CardEntity setMemoryBus(Integer memoryBus) {
        this.memoryBus = memoryBus;
        return this;
    }
}