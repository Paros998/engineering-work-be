package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.card.CardEntity} entity
 */
@AllArgsConstructor
@Builder
@Getter
public class GpuData implements Serializable {
    private final UUID productId;
    private final String title;

    private final String productPhoto;
}