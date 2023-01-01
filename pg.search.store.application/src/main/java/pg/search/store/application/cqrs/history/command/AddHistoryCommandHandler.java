package pg.search.store.application.cqrs.history.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.history.HistoryMapper;
import pg.search.store.infrastructure.history.HistoryRepository;

@Service
@AllArgsConstructor
public class AddHistoryCommandHandler implements CommandHandler<AddHistoryCommand, Void> {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public Void handle(final AddHistoryCommand command) {
        final var history = historyMapper.toHistoryEntity(command.getHistoryData(), command.getUserId());
        historyRepository.save(history);
        return null;
    }
}