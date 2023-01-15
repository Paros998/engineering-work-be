package pg.search.store.application.cqrs.crypto.bitcoin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.crypto.bitcoin.BitcoinData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class BitcoinDataQuery implements Query<BitcoinData> {
    private UUID userId;

}
