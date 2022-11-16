package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.review.ReviewService;

@Service
@AllArgsConstructor
public class ChangeReviewCensoringCommandHandler implements CommandHandler<ChangeReviewCensoringCommand, Void> {
    private final ReviewService reviewService;

    @Override
    public Void handle(final ChangeReviewCensoringCommand command) {
        reviewService.switchIsCensored(command.getReviewId());

        return null;
    }
}