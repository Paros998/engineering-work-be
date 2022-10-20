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
public class GraphicCardEntity extends ProductEntity {
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

    private String coreClock;

    private Integer memoryAmount;

    @Enumerated(EnumType.STRING)
    private MemoryType typeOfMemory;

    @Enumerated(EnumType.STRING)
    private PciType typeOfPciConnector;

    private Integer memoryBus;
}