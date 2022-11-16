package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.review.Review;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.review.ReviewMapper;
import pg.search.store.infrastructure.review.ReviewService;

@Service
@AllArgsConstructor

public class ProductReviewsQueryHandler implements QueryHandler<ProductReviewsQuery, PageResponse<Review>> {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public PageResponse<Review> handle(final ProductReviewsQuery query) {
        final var request = PageMapper.toSpringPageRequest(query.getPageRequest());

        final var result = reviewService.getReviews(request, query.getProductId());

        return PageMapper.toPageResponse(result, reviewMapper::toReview);
    }
}