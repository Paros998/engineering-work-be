package pg.search.store.infrastructure.product.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import pg.lib.filters.common.Criteria;
import pg.lib.filters.common.JunctionType;

import pg.search.store.infrastructure.product.ProductEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@Builder
public class ResolvedFilter {
    private final Map<Class<? extends ProductEntity>, Map<JunctionType, List<Criteria>>> filters;

    private String cacheMeta;

    public ResolvedFilter() {
        this.filters = new HashMap<>();
    }
}
