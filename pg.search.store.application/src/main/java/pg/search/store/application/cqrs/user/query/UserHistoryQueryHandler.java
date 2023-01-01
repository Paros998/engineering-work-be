package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.history.HistoryData;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.history.HistoryEntity;
import pg.search.store.infrastructure.history.HistoryMapper;
import pg.search.store.infrastructure.history.HistoryRepository;

@Service
@AllArgsConstructor
public class UserHistoryQueryHandler implements QueryHandler<UserHistoryQuery, PageResponse<HistoryData>> {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public PageResponse<HistoryData> handle(final UserHistoryQuery query) {
        SpringPageRequest pageRequest = PageMapper.toSpringPageRequest(query.getRequest());

        final var history = new SpringPageResponse<>(historyRepository.findByUserUserId(query.getUserId(),
                pageRequest.getRequest(HistoryEntity.class)));

        return PageMapper.toPageResponse(
                history,
                historyMapper::toHistoryData,
                null
        );
    }
}
