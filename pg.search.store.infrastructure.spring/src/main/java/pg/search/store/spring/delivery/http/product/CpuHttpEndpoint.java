package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.cpu.AddCpuCommand;
import pg.search.store.application.cqrs.product.query.cpu.CpuDataQuery;
import pg.search.store.domain.product.cpu.CpuData;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.CPU_PATH)
@AllArgsConstructor
@Tag(name = "Product-Cpu")
public class CpuHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{cpuId}")
    public CpuData getCpu(final @NonNull @PathVariable UUID cpuId) {
        final var query = CpuDataQuery.of(cpuId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("AddCpuCommand")
    public UUID createCpu(final @Valid @NonNull @RequestBody AddCpuCommand command) {
        return serviceExecutor.executeCommand(command);
    }

}