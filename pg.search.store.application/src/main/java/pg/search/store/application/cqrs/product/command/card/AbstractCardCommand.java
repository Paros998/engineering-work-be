package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AbstractCardCommand {
    protected String title;
    protected String producentCode;
    protected String producentSite;
    protected String dateOfProduction;
    protected String cardModel;
    protected String technology;
    protected Boolean rtxSupport;
    protected List<String> supportedLibraries;
    protected Integer cudaCoresAmount;
    protected Integer powerConsumption;
    protected Integer recommendedPower;
    protected String cooling;
    protected String powerConnector;
    protected Float coreClock;
    protected Float boostCoreClock;
    protected Float memoryAmount;
    protected Float supportedDirectX;
    protected String typeOfMemory;
    protected String typeOfPciConnector;
    protected Float memoryClock;
    protected Integer memoryBus;
    protected Integer maxNumberOfUnitsInSLI;
    protected Float bitcoinHashRate;
}
