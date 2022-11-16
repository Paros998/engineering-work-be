package pg.search.store.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@Data
public class UserConsensus {
    private Double score;
    private Integer totalReviews;
}