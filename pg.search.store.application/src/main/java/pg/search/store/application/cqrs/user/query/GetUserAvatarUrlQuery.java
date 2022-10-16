package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class GetUserAvatarUrlQuery implements Query<String> {
    private final UUID userId;
}
