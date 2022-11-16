package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.review.query.OptionalReviewQuery;
import pg.search.store.application.cqrs.review.query.ProductReviewsQuery;
import pg.search.store.application.cqrs.review.query.ScoreChartQuery;
import pg.search.store.application.cqrs.review.query.UserConsensusQuery;
import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.review.Review;
import pg.search.store.domain.review.ScoreChart;
import pg.search.store.domain.review.UserConsensus;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.PRODUCT_REVIEWS_PATH)
@AllArgsConstructor
@Tag(name = "Product-Reviews")
public class ProductReviewsHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{productId}")
    public PageResponse<Review> getProductReviews(final @PathVariable @NonNull UUID productId,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                                  @RequestParam(required = false, defaultValue = "reviewDate") String sortBy) {
        final var query = ProductReviewsQuery.of(productId, new PageRequest(page, pageLimit, sortDir, sortBy));
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{productId}/user-consensus")
    public UserConsensus getUserConsensus(final @PathVariable @NonNull UUID productId) {
        final var query = UserConsensusQuery.of(productId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{productId}/chart")
    public ScoreChart getScoreChart(final @PathVariable @NonNull UUID productId) {
        final var query = ScoreChartQuery.of(productId);
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{productId}/user/{userId}")
    public Optional<Review> getUserReview(final @PathVariable @NonNull UUID productId,
                                          final @PathVariable @NonNull UUID userId) {
        final var query = OptionalReviewQuery.of(userId, productId);
        return serviceExecutor.executeQuery(query);
    }
}