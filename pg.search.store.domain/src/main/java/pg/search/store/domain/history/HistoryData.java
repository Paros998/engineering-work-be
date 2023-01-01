package pg.search.store.domain.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link pg.search.store.infrastructure.history.HistoryEntity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HistoryData implements Serializable {
    private UUID historyId;
    private UUID productId;
    private String action;
    private String content;
    private String dateTime;
}