package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.review.Review;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class OptionalReviewQuery implements Query<Optional<Review>> {
    private final UUID userId;
    private final UUID productId;
}
