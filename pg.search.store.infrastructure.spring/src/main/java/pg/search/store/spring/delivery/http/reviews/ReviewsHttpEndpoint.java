package pg.search.store.spring.delivery.http.reviews;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.review.command.AddReviewCommand;
import pg.search.store.application.cqrs.review.command.ChangeReviewCensoringCommand;
import pg.search.store.application.cqrs.review.command.UpdateReviewCommand;
import pg.search.store.domain.review.Review;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.REVIEWS_PATH)
@AllArgsConstructor
@Tag(name = "Reviews")
public class ReviewsHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @PostMapping
    public void addReview(final @Valid @NonNull @RequestBody Review review) {
        final var command = AddReviewCommand.of(review);
        serviceExecutor.executeCommand(command);
    }

    @PutMapping
    public void updateReview(final @Valid @NonNull @RequestBody Review review) {
        final var command = UpdateReviewCommand.of(review);
        serviceExecutor.executeCommand(command);
    }

    @PatchMapping("{reviewId}")
    public void changeReviewCensoring(final @Valid @NonNull @PathVariable UUID reviewId) {
        final var command = ChangeReviewCensoringCommand.of(reviewId);
        serviceExecutor.executeCommand(command);
    }

}