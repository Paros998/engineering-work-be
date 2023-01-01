package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BasicProductWithPerformance extends BasicProduct {
    private Double peakPerformance;
    private Double avgPerformance;

    @Override
    public BasicProductWithPerformance setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    @Override
    public BasicProductWithPerformance setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public BasicProductWithPerformance setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public BasicProductWithPerformance setFollowed(Boolean followed) {
        this.isFollowed = followed;
        return this;
    }

    @Override
    public BasicProductWithPerformance setMarked(Boolean marked) {
        this.isMarked = marked;
        return this;
    }

    @Override
    public BasicProductWithPerformance setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    @Override
    public BasicProductWithPerformance setStoresNumber(Integer storesNumber) {
        this.storesNumber = storesNumber;
        return this;
    }

    @Override
    public BasicProductWithPerformance setStoresLowestPrice(String storesLowestPrice) {
        this.storesLowestPrice = storesLowestPrice;
        return this;
    }

    @Override
    public BasicProductWithPerformance setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    public BasicProductWithPerformance setPeakPerformance(final Double peakPerformance) {
        BigDecimal bigDecimal = BigDecimal.valueOf(peakPerformance).setScale(2, RoundingMode.CEILING);
        this.peakPerformance = bigDecimal.doubleValue();
        return this;
    }

    public BasicProductWithPerformance setAvgPerformance(final Double avgPerformance) {
        BigDecimal bigDecimal = BigDecimal.valueOf(avgPerformance).setScale(2, RoundingMode.CEILING);
        this.avgPerformance = bigDecimal.doubleValue();
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
