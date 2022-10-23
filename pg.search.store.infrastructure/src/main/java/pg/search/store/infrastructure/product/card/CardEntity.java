package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.ProductType;
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

    public CardEntity setCoreClock(final Integer coreClock) {
        this.coreClock = coreClock;
        return this;
    }

    public CardEntity setBoostCoreClock(final Integer boostCoreClock) {
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

    public CardEntity setMemoryClock(final Integer memoryClock) {
        this.memoryClock = memoryClock;
        return this;
    }

    public CardEntity setMemoryBus(final Integer memoryBus) {
        this.memoryBus = memoryBus;
        return this;
    }
}