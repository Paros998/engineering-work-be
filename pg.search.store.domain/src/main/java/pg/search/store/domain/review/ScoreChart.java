package pg.search.store.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScoreChart {
    private Integer totalCount;
    private Integer[] count;

    @Override
    public String toString() {
        return "{%s: {totalCount: %d, count: [%s]}}".formatted(this.getClass(), this.totalCount, this.count);
    }
}