package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import pg.search.store.domain.user.UserData;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserByIdQuery implements Query<UserData> {
    private final UUID userId;
}
