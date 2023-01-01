package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.pc.AddPcCommand;
import pg.search.store.application.cqrs.product.query.pc.PcDataQuery;
import pg.search.store.domain.product.pc.PcData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.PC_PATH)
@AllArgsConstructor
@Tag(name = "Product-PC")
public class PcHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{pcId}")
    public PcData getPc(final @NonNull @PathVariable UUID pcId) {
        final var query = PcDataQuery.of(pcId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("AddPcCommand")
    public UUID addPcProduct(final @Valid @NonNull @RequestBody AddPcCommand command) {
        return serviceExecutor.executeCommand(command);
    }

}