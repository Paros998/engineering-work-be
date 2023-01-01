package pg.search.store.application.cqrs.product.query.pc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.pc.PcData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class PcDataQuery implements Query<PcData> {
    private UUID productId;
}
