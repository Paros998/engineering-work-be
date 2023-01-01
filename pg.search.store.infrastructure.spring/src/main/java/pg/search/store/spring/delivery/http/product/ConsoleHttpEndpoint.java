package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.console.AddConsoleCommand;
import pg.search.store.application.cqrs.product.query.console.ConsoleDataQuery;
import pg.search.store.domain.product.console.ConsoleData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.CONSOLE_PATH)
@AllArgsConstructor
@Tag(name = "Product-Console")
public class ConsoleHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{consoleId}")
    public ConsoleData getConsole(final @NonNull @PathVariable UUID consoleId) {
        final var query = ConsoleDataQuery.of(consoleId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("AddConsoleCommand")
    public UUID createConsole(final @Valid @NonNull @RequestBody AddConsoleCommand command) {
        return serviceExecutor.executeCommand(command);
    }

}