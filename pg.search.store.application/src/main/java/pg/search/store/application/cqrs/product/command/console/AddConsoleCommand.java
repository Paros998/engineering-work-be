package pg.search.store.application.cqrs.product.command.console;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Data
public class AddConsoleCommand implements Command<UUID> {
    private String title;

    private String producentCode;

    private String producentSite;

    private String dateOfProduction;

    private String platform;

    private String console;

    private String producer;
}