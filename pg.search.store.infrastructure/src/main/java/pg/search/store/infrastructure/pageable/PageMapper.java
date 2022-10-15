package pg.search.store.infrastructure.pageable;

import lombok.experimental.UtilityClass;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;

@UtilityClass
public class PageMapper {

    <T> PageResponse<T> toPageResponse(final SpringPageResponse<T> response) {
        return new PageResponse<>(response.getCurrentPage(), response.getTotalPages(), response.getContent());
    }

    SpringPageRequest toSpringPageRequest(final PageRequest request) {
        return new SpringPageRequest(request.page(), request.pageLimit(), request.sortDir(), request.sortBy());
    }
}
