package pg.search.store.application.cqrs.user.command;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.domain.user.UserData;
import pg.search.store.infrastructure.user.UserMapper;
import pg.search.store.infrastructure.user.UserService;

@Service
@AllArgsConstructor
public class RegisterClientCommandHandler implements CommandHandler<RegisterClientCommand, UserData> {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserData handle(final RegisterClientCommand command) {
        final var user = userService.createClient(command.getClientData());
        return userMapper.toUserData(user);
    }
}
