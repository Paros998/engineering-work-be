package pg.search.store.application.cqrs.product.query.cpu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.cpu.CpuData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class CpuDataQuery implements Query<CpuData> {
    private UUID productId;
}
