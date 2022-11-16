package pg.search.store.infrastructure.review;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.search.store.domain.review.ScoreChart;
import pg.search.store.domain.review.UserConsensus;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewEntity getReview(final UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(reviewId, ReviewEntity.class));
    }

    @Override
    public SpringPageResponse<ReviewEntity> getReviews(final @NonNull SpringPageRequest request, final UUID productId) {
        return new SpringPageResponse<>(reviewRepository.findByProductId(productId, request.getRequest(ReviewEntity.class)));
    }

    @Override
    public UserConsensus getUserConsensus(final @NonNull UUID productId) {
        List<ReviewEntity> reviews = reviewRepository.findAllByProductId(productId);

        final AtomicReference<Double> score = new AtomicReference<>(0d);

        reviews.forEach(review -> score.updateAndGet(v -> v + review.getScore()));

        return UserConsensus.builder()
                .score(reviews.isEmpty() ? 0 : score.get() / reviews.size())
                .totalReviews(reviews.size())
                .build();
    }

    @Override
    public ScoreChart getScoreChart(final UUID productId) {
        final List<ReviewEntity> reviews = reviewRepository.findAllByProductId(productId);

        final Integer[] count = {0, 0, 0, 0, 0, 0};

        reviews.forEach(review -> count[review.getScore()] += 1);

        return ScoreChart.builder()
                .totalCount(reviews.size())
                .count(count)
                .build();
    }

    @Override
    public Optional<ReviewEntity> getUserReview(final UUID productId, final UUID userId) {
        return reviewRepository.findByUserIdAndProductId(userId, productId);
    }

    @Override
    @Transactional
    public void saveReviewEntity(final ReviewEntity review) {
        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void switchIsCensored(final UUID reviewId) {
        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);

        if (review.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Review censor/un censor | Review with id: %s not exists".formatted(reviewId));

        reviewRepository.switchCensoring(reviewId);
    }

}