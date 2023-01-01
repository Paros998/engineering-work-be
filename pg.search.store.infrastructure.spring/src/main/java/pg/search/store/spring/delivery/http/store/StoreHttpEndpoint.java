package pg.search.store.spring.delivery.http.store;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.store.command.AddStoreCommand;

import javax.validation.Valid;

import java.util.UUID;

import static pg.search.store.spring.delivery.http.common.HttpCommonHelper.STORE_PATH;

@RestController
@RequestMapping(STORE_PATH)
@RequiredArgsConstructor
@Tag(name = "Store")
public class StoreHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @PostMapping("AddStoreCommand")
    public UUID addStore(final @NonNull @Valid @RequestBody AddStoreCommand command) {
        return serviceExecutor.executeCommand(command);
    }
}
