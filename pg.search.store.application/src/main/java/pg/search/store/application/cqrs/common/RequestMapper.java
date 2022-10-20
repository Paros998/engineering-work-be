package pg.search.store.application.cqrs.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import pg.search.store.application.cqrs.user.command.CreateUserCommand;
import pg.search.store.domain.user.UserCredentialsData;

@UtilityClass
public class RequestMapper {
    public UserCredentialsData toUserCredentialsData(final @NonNull CreateUserCommand command) {
        return UserCredentialsData.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .role(command.getRole())
                .username(command.getUsername())
                .build();
    }
}
