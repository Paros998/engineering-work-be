package pg.search.store.application.cqrs.review.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.review.UserConsensus;
import pg.search.store.infrastructure.review.ReviewService;

@Service
@AllArgsConstructor
public class UserConsensusQueryHandler implements QueryHandler<UserConsensusQuery, UserConsensus> {
    private final ReviewService reviewService;

    public UserConsensus handle(final UserConsensusQuery query) {
        return reviewService.getUserConsensus(query.getProductId());
    }
}