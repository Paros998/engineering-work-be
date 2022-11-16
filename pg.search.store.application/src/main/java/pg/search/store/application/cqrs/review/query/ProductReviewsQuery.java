package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.review.Review;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ProductReviewsQuery implements Query<PageResponse<Review>> {
    private final UUID productId;
    private final PageRequest pageRequest;
}
