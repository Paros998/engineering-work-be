package pg.search.store.domain.common;

public record PageRequest(Integer page, Integer pageLimit, String sortDir, String sortBy) {
}
