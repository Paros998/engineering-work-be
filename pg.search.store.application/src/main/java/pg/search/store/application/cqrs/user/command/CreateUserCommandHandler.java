package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.domain.user.UserData;
import pg.search.store.infrastructure.user.UserMapper;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserData> {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserData handle(final CreateUserCommand command) {
        final var data = RequestMapper.toUserCredentialsData(command);
        return userMapper.toUserData(userService.createUser(data));
    }
}
