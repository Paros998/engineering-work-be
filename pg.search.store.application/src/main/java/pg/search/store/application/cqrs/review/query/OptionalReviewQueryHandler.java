package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.review.Review;
import pg.search.store.infrastructure.review.ReviewMapper;
import pg.search.store.infrastructure.review.ReviewService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OptionalReviewQueryHandler implements QueryHandler<OptionalReviewQuery, Optional<Review>> {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public Optional<Review> handle(final OptionalReviewQuery query) {
        return reviewService.getUserReview(query.getProductId(), query.getUserId())
                .map(reviewMapper::toReview);
    }
}