package pg.search.store.application.cqrs.product.query.laptop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.product.laptop.LaptopData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class LaptopDataQuery implements Query<LaptopData> {
    private UUID productId;
}
