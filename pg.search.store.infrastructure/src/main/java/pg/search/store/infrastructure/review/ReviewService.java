package pg.search.store.infrastructure.review;

import pg.search.store.domain.review.ScoreChart;
import pg.search.store.domain.review.UserConsensus;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.Optional;
import java.util.UUID;

public interface ReviewService {
    ReviewEntity getReview(UUID reviewId);

    SpringPageResponse<ReviewEntity> getReviews(SpringPageRequest request, UUID productId);

    UserConsensus getUserConsensus(UUID productId);

    ScoreChart getScoreChart(UUID productId);

    Optional<ReviewEntity> getUserReview(UUID productId, UUID userId);

    void saveReviewEntity(ReviewEntity review);

    void switchIsCensored(UUID reviewId);
}
