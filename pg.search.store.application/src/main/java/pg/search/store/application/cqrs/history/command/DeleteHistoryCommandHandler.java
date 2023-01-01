package pg.search.store.application.cqrs.history.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.history.HistoryRepository;

@Service
@AllArgsConstructor
public class DeleteHistoryCommandHandler implements CommandHandler<DeleteHistoryCommand, Void> {
    private final HistoryRepository historyRepository;

    @Override
    public Void handle(final DeleteHistoryCommand command) {
        historyRepository.deleteById(command.getHistoryId());
        return null;
    }
}
