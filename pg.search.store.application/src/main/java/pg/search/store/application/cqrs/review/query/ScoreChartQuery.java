package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.review.ScoreChart;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class ScoreChartQuery implements Query<ScoreChart> {
    private final UUID productId;
}
