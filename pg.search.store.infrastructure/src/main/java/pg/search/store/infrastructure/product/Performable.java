package pg.search.store.infrastructure.product;

public interface Performable {
    default Double getPeakPerformance(final Double base) {
        return 100.0;
    }

    default Double getAvgPerformance(final Double base) {
        return 100.0;
    }
}
