package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.review.UserConsensus;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserConsensusQuery implements Query<UserConsensus> {
    private final UUID productId;
}
