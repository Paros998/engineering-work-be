package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.review.ReviewMapper;
import pg.search.store.infrastructure.review.ReviewService;
import pg.search.store.infrastructure.review.validator.ReviewCreateValidator;

@Service
@AllArgsConstructor
public class AddReviewCommandHandler implements CommandHandler<AddReviewCommand, Void> {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ReviewCreateValidator validator;

    public Void handle(final AddReviewCommand command) {
        validator.validate(command.getReview());

        final var review = reviewMapper.createReviewEntity(command.getReview());

        reviewService.saveReviewEntity(review);

        return null;
    }
}