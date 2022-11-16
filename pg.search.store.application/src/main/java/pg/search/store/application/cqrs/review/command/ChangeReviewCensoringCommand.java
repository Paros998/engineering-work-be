package pg.search.store.application.cqrs.review.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ChangeReviewCensoringCommand implements Command<Void> {
    private final UUID reviewId;
}