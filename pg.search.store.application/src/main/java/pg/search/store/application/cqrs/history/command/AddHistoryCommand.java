package pg.search.store.application.cqrs.history.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import pg.search.store.domain.history.HistoryData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class AddHistoryCommand implements Command<Void> {
    private HistoryData historyData;
    private UUID userId;
}
