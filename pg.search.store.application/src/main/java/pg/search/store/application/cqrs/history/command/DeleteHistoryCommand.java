package pg.search.store.application.cqrs.history.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class DeleteHistoryCommand implements Command<Void> {
    private UUID historyId;
}
