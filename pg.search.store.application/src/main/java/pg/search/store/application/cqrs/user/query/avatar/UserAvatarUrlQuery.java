package pg.search.store.application.cqrs.user.query.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

import pg.lib.cqrs.query.Query;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UserAvatarUrlQuery implements Query<String> {
    private final UUID userId;
}
