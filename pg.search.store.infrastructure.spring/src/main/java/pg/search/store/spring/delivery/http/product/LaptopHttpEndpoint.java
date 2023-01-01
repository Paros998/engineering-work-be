package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.laptop.AddLaptopCommand;
import pg.search.store.application.cqrs.product.query.laptop.LaptopDataQuery;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.LAPTOP_PATH)
@AllArgsConstructor
@Tag(name = "Product-Laptop")
public class LaptopHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{laptopId}")
    public LaptopData getLaptop(final @NonNull @PathVariable UUID laptopId) {
        final var query = LaptopDataQuery.of(laptopId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("AddLaptopCommand")
    public UUID addLaptopProduct(final @Valid @NonNull @RequestBody AddLaptopCommand command) {
        return serviceExecutor.executeCommand(command);
    }

}