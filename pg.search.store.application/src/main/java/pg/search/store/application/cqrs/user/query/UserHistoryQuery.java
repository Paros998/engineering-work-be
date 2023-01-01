package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.history.HistoryData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserHistoryQuery implements Query<PageResponse<HistoryData>> {
    private final PageRequest request;
    private final UUID userId;
}
