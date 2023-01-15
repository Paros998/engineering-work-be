package pg.search.store.infrastructure.product.cpu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.console.CpuProducer;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.laptop.LaptopEntity;
import pg.search.store.infrastructure.product.pc.PcEntity;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "processors")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CpuEntity extends ProductEntity {
    @Enumerated(EnumType.STRING)
    private CpuProducer producer;

    private String cpuModel;

    private Float maxTdp;

    private String technology;

    private Float coreClock;

    private Float boostCoreClock;

    private String totalSpecification;

    private String socket;

    private String series;

    private String version;

    private Integer cores;

    private Integer threads;

    private Integer instructionsPerCycle;

    private boolean onlyLaptopCpu;

    // in GFLOPs
    private Double basePerformance;
    private Double boostPerformance;

    @OneToMany(mappedBy = "cpu")
    @JsonManagedReference
    private List<PcEntity> usedInPcEntities;

    @OneToMany(mappedBy = "cpu")
    @JsonManagedReference
    private List<LaptopEntity> usedInLaptopEntities;

    @PrePersist
    @PreUpdate
    void calculatePerformance() {
        boolean dataAvailable = instructionsPerCycle != null && cores != null;

        if (dataAvailable && coreClock != null) {
            basePerformance = (double) (coreClock * cores * instructionsPerCycle);
        }
        if (dataAvailable && boostCoreClock != null) {
            boostPerformance = (double) (boostCoreClock * cores * instructionsPerCycle);
        }
    }

    @Override
    public Double getPeakPerformance(Double base) {
        if (boostPerformance != null)
            return (boostPerformance / base) * 100;
        return 0.0;
    }

    @Override
    public Double getAvgPerformance(Double base) {
        if (basePerformance != null)
            return (basePerformance / base) * 100;
        return 0.0;
    }

    @Override
    public CpuEntity setProductType(final ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public CpuEntity setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public CpuEntity setProducentCode(final String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    @Override
    public CpuEntity setProductPhoto(final FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    @Override
    public CpuEntity setProducentSite(final String producentSite) {
        this.producentSite = producentSite;
        return this;
    }

    @Override
    public CpuEntity setDateOfProduction(final LocalDate dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
        return this;
    }

    public CpuEntity setProducer(final CpuProducer producer) {
        this.producer = producer;
        return this;
    }

    public CpuEntity setOnlyLaptopCpu(boolean onlyLaptopCpu) {
        this.onlyLaptopCpu = onlyLaptopCpu;
        return this;
    }

    public CpuEntity setCpuModel(final String cpuModel) {
        this.cpuModel = cpuModel;
        return this;
    }

    public CpuEntity setMaxTdp(final Float maxTdp) {
        this.maxTdp = maxTdp;
        return this;
    }

    public CpuEntity setTechnology(final String technology) {
        this.technology = technology;
        return this;
    }

    public CpuEntity setCoreClock(final Float coreClock) {
        this.coreClock = coreClock;
        return this;
    }

    public CpuEntity setBoostCoreClock(final Float boostCoreClock) {
        this.boostCoreClock = boostCoreClock;
        return this;
    }

    public CpuEntity setTotalSpecification(final String totalSpecification) {
        this.totalSpecification = totalSpecification;
        return this;
    }

    public CpuEntity setSocket(final String socket) {
        this.socket = socket;
        return this;
    }

    public CpuEntity setSeries(final String series) {
        this.series = series;
        return this;
    }

    public CpuEntity setVersion(final String version) {
        this.version = version;
        return this;
    }

    public CpuEntity setCores(final Integer cores) {
        this.cores = cores;
        return this;
    }

    public CpuEntity setThreads(final Integer threads) {
        this.threads = threads;
        return this;
    }

    public CpuEntity setInstructionsPerCycle(final Integer instructionsPerCycle) {
        this.instructionsPerCycle = instructionsPerCycle;
        return this;
    }

    public CpuEntity setBasePerformance(final Double basePerformance) {
        this.basePerformance = basePerformance;
        return this;
    }

    public CpuEntity setBoostPerformance(final Double boostPerformance) {
        this.boostPerformance = boostPerformance;
        return this;
    }
}