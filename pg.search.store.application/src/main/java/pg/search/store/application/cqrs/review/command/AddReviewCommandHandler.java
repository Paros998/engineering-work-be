package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.review.ReviewMapper;
import pg.search.store.infrastructure.review.ReviewService;
import pg.search.store.infrastructure.review.validator.ReviewCreateValidator;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddReviewCommandHandler implements CommandHandler<AddReviewCommand, Void> {
    private final ReviewService reviewService;
    private final NotificationService notificationService;
    private final ReviewMapper reviewMapper;
    private final ReviewCreateValidator validator;

    public Void handle(final AddReviewCommand command) {
        validator.validate(command.getReview());

        final var review = reviewMapper.createReviewEntity(command.getReview());

        UUID reviewId = reviewService.saveReviewEntity(review);

        UUID productId = review.getProduct().getProductId();
        notificationService.fireNotifications(NotificationType.FOLLOWED_NEW_REVIEW, productId.toString());
        notificationService.fireNotifications(NotificationType.MARKED_NEW_REVIEW, productId.toString());

        return null;
    }
}