package pg.search.store.domain.product.cpu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.product.console.CpuProducer;

import java.io.Serializable;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.cpu.CpuEntity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CpuData implements Serializable {
    public static final ProductType productType = ProductType.CPU;
    private String title;
    private String producentCode;
    private String producentSite;
    private String dateOfProduction;
    private String productPhoto;

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
}