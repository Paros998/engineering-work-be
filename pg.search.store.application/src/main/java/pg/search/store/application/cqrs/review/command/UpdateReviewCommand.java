package pg.search.store.application.cqrs.review.command;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.review.Review;

public class UpdateReviewCommand extends AbstractReviewCommand implements Command<Void> {

    public UpdateReviewCommand(Review review) {
        super(review);
    }

    public static UpdateReviewCommand of(final Review review) {
        return new UpdateReviewCommand(review);
    }
}
