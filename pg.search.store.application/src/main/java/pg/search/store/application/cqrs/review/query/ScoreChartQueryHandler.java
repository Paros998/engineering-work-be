package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.review.ScoreChart;
import pg.search.store.infrastructure.review.ReviewService;

@Service
@AllArgsConstructor
public class ScoreChartQueryHandler implements QueryHandler<ScoreChartQuery, ScoreChart> {
    private final ReviewService reviewService;

    public ScoreChart handle(final ScoreChartQuery query) {
        return reviewService.getScoreChart(query.getProductId());
    }
}
