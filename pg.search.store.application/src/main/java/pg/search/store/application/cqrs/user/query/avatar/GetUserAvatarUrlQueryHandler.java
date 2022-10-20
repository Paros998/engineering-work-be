package pg.search.store.application.cqrs.user.query.avatar;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class GetUserAvatarUrlQueryHandler implements QueryHandler<GetUserAvatarUrlQuery, String> {
    private final UserService userService;

    public String handle(final GetUserAvatarUrlQuery query) {
        return userService.getUserAvatar(query.getUserId());
    }
}
