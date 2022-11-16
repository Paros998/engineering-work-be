package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.domain.review.Review;
import pg.search.store.infrastructure.review.ReviewEntity;
import pg.search.store.infrastructure.review.ReviewMapper;
import pg.search.store.infrastructure.review.ReviewService;
import pg.search.store.infrastructure.review.validator.ReviewUpdateValidator;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateReviewCommandHandler implements CommandHandler<UpdateReviewCommand, Void> {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ReviewUpdateValidator validator;

    @Override
    public Void handle(final UpdateReviewCommand command) {
        final Review reviewData = command.getReview();

        validator.validate(reviewData);

        reviewData.setReviewDate(LocalDateTime.now().toString());
        reviewData.setIsCensored(reviewService.getReview(reviewData.getId()).getIsCensored());

        final ReviewEntity review = reviewMapper.toReviewEntity(reviewData);

        reviewService.saveReviewEntity(review);

        return null;
    }
}