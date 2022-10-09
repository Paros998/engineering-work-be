package pg.search.store.domain.common;

import java.util.List;

public record PageResponse<T>(Integer currentPage, Integer totalPages, List<T> content) {
}
