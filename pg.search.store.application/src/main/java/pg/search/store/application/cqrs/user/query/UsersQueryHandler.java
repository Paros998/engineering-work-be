package pg.search.store.application.cqrs.user.query;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.query.QueryHandler;

import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.user.UserData;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserMapper;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class UsersQueryHandler implements QueryHandler<UsersQuery, PageResponse<UserData>> {
    private final UserService userService;
    private final UserMapper userMapper;

    public PageResponse<UserData> handle(final UsersQuery query) {
        final SpringPageResponse<UserEntity> users = userService.getUsers(PageMapper.toSpringPageRequest(query.getPageRequest()));

        return PageMapper.toPageResponse(users, userMapper::toUserData);
    }
}
