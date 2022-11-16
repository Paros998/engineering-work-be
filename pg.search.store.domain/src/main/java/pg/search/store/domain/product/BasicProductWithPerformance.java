package pg.search.store.domain.product;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class BasicProductWithPerformance extends BasicProduct {
    private Float peakPerformance;
    private Float avgPerformance;

    public BasicProductWithPerformance setPeakPerformance(final Float peakPerformance) {
        this.peakPerformance = peakPerformance;
        return this;
    }

    public BasicProductWithPerformance setAvgPerformance(final Float avgPerformance) {
        this.avgPerformance = avgPerformance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BasicProductWithPerformance that = (BasicProductWithPerformance) o;
        return Objects.equals(peakPerformance, that.peakPerformance) && Objects.equals(avgPerformance, that.avgPerformance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), peakPerformance, avgPerformance);
    }
}
