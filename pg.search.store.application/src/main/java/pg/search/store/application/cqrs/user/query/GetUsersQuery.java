package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.common.PageRequest;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.user.UserData;

@AllArgsConstructor(staticName = "of")
@Getter
public class GetUsersQuery implements Query<PageResponse<UserData>> {
    private final PageRequest pageRequest;
}
