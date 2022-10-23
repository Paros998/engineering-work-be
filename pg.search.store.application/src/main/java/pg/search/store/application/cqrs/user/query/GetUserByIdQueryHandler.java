package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.user.UserData;
import pg.search.store.infrastructure.user.UserMapper;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, UserData> {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserData handle(final GetUserByIdQuery query) {
        return userMapper.toUserData(
                userService.getUser(query.getUserId())
        );
    }
}
