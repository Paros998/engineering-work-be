package pg.search.store.domain.product.console;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.search.store.domain.game.Platform;
import pg.search.store.domain.product.Console;
import pg.search.store.domain.product.ProductType;

import java.io.Serializable;

/**
 * A DTO for the {@link pg.search.store.infrastructure.product.console.ConsoleEntity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConsoleData implements Serializable {
    public static final ProductType productType = ProductType.CONSOLE;
    private String title;
    private String producentCode;
    private String productPhoto;
    private String producentSite;
    private String dateOfProduction;
    private ConsoleProducer producer;
    private Console console;
    private Platform platform;
}