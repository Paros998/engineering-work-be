package pg.search.store.infrastructure.history;

import pg.search.store.domain.history.HistoryData;

import java.util.UUID;

public interface HistoryMapper {
    HistoryData toHistoryData(HistoryEntity history);

    HistoryEntity toHistoryEntity(HistoryData data, UUID userId);
}
