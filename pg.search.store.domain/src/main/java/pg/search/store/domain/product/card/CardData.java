package pg.search.store.domain.product.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.search.store.domain.product.ProductType;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.card.CardEntity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CardData implements Serializable {
    public static final ProductType productType = ProductType.GPU;
    private String cardModel;
    private String title;
    private String producentCode;
    private String producentSite;
    private String dateOfProduction;
    private String productPhoto;
    private Technology technology;
    private Boolean rtxSupport;
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
    private MemoryType typeOfMemory;
    private PciType typeOfPciConnector;
    private Float memoryClock;
    private Integer memoryBus;
    private Integer maxNumberOfUnitsInSLI;
}