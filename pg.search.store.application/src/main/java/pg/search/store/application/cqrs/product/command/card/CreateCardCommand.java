package pg.search.store.application.cqrs.product.command.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class CreateCardCommand implements Command<UUID> {
    private String title;
    private String producentCode;
    private String producentSite;
    private String technology;
    private Boolean rtxSupport;
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
    private String typeOfMemory;
    private String typeOfPciConnector;
    private Integer memoryClock;
    private Integer memoryBus;
}
