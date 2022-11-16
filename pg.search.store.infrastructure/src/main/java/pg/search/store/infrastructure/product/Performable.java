package pg.search.store.infrastructure.product;

public interface Performable {
    Float getPeakPerformance(Float base);

    Float getAvgPerformance(Float base);
}
