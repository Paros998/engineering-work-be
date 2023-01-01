package pg.search.store.spring.delivery.http.history;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.history.command.AddHistoryCommand;
import pg.search.store.application.cqrs.history.command.DeleteHistoryCommand;
import pg.search.store.application.cqrs.user.query.UserHistoryQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.history.HistoryData;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.HISTORY_PATH)
@AllArgsConstructor
@Tag(name = "History")
public class HistoryHttpEndpoint {
    private final ServiceExecutor serviceExecutor;


    @GetMapping("{userId}")
    public PageResponse<HistoryData> getUserHistory(final @PathVariable @NonNull UUID userId,
                                                    final @RequestParam(required = false, defaultValue = "1") Integer page,
                                                    final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
                                                    final @RequestParam(required = false, defaultValue = "desc") String sortDir,
                                                    final @RequestParam(required = false, defaultValue = "dateTime") String sortBy
    ) {
        final var query = UserHistoryQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy), userId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping("/AddHistoryCommand")
    public Void addHistory(final @NonNull @RequestBody @Valid AddHistoryCommand command) {
        return serviceExecutor.executeCommand(command);
    }

    @DeleteMapping("{historyId}")
    public Void deleteHistory(final @PathVariable @NonNull UUID historyId) {
        final var command = DeleteHistoryCommand.of(historyId);
        return serviceExecutor.executeCommand(command);
    }
}