package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
@Data
public class Performance {
    private Float peakPerformance;
    private Float averagePerformance;
}
