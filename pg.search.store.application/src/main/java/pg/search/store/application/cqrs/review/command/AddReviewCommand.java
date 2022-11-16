package pg.search.store.application.cqrs.review.command;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.review.Review;

public class AddReviewCommand extends AbstractReviewCommand implements Command<Void> {

    public AddReviewCommand(Review review) {
        super(review);
    }

    public static AddReviewCommand of(final Review review) {
        return new AddReviewCommand(review);
    }
}
