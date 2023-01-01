package pg.search.store.infrastructure.product.filters;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import pg.lib.filters.specification.SpecificationBuilder;

import pg.search.store.infrastructure.product.ProductEntity;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
@Data
@Builder
public class ResolvedFilter {
    private final Class<? extends ProductEntity> clazz;
    private final List<SpecificationBuilder> specificationBuilders;
}