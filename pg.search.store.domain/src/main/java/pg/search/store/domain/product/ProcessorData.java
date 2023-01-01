package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import pg.search.store.domain.product.console.CpuProducer;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.cpu.CpuEntity} entity
 */
@AllArgsConstructor
@Builder
@Getter
public class ProcessorData implements Serializable {
    private final UUID productId;
    private final String title;
    private final CpuProducer producer;

    private final String productPhoto;
}