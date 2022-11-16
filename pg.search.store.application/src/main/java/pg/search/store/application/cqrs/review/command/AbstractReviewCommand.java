package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import pg.search.store.domain.review.Review;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AbstractReviewCommand {
    protected Review review;
}
