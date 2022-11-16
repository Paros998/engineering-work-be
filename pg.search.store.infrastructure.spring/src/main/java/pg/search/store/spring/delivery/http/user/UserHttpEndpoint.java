package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.user.command.CreateUserCommand;
import pg.search.store.application.cqrs.user.command.DeleteUserCommand;
import pg.search.store.application.cqrs.user.command.RegisterClientCommand;
import pg.search.store.application.cqrs.user.command.UpdateUserDataCommand;
import pg.search.store.application.cqrs.user.command.auth.ChangeUserStateCommand;
import pg.search.store.application.cqrs.user.command.auth.UpdateUserPasswordCommand;
import pg.search.store.application.cqrs.user.query.UserByIdQuery;
import pg.search.store.application.cqrs.user.query.UsersQuery;
import pg.search.store.domain.common.PageResponse;
import pg.search.store.domain.user.ChangePasswordData;
import pg.search.store.domain.user.RegisterClientData;
import pg.search.store.domain.user.UpdateUsernameEmailData;
import pg.search.store.domain.user.UserData;
import pg.search.store.infrastructure.common.pageable.PageMapper;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.USER_PATH)
@AllArgsConstructor
@Tag(name = "User")
public class UserHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping
    public PageResponse<UserData> getUsers(
            final @RequestParam(required = false, defaultValue = "1") Integer page,
            final @RequestParam(required = false, defaultValue = "10") Integer pageLimit,
            final @RequestParam(required = false, defaultValue = "asc") String sortDir,
            final @RequestParam(required = false, defaultValue = "username") String sortBy
    ) {
        final UsersQuery query = UsersQuery.of(PageMapper.toPageRequest(page, pageLimit, sortDir, sortBy));
        return serviceExecutor.executeQuery(query);
    }

    @GetMapping("{userId}")
    public UserData getUserById(final @PathVariable @NonNull UUID userId) {
        return serviceExecutor.executeQuery(UserByIdQuery.of(userId));
    }

    @PostMapping("CreateUserCommand")
    public UserData createAppUser(final @Valid @NonNull @RequestBody CreateUserCommand command) {
        return serviceExecutor.executeCommand(command);
    }

    @PostMapping("/register-client")
    public UserData registerClient(final @Valid @NonNull @RequestBody RegisterClientData clientData) {
        return serviceExecutor.executeCommand(RegisterClientCommand.of(clientData));
    }

    @PutMapping("{userId}/user-data")
    public void updateAppUser(final @NonNull @PathVariable UUID userId, final @Valid @NonNull @RequestBody UpdateUsernameEmailData updateData) {
        serviceExecutor.executeCommand(UpdateUserDataCommand.of(userId, updateData));
    }

    @PutMapping("{userId}/change-password")
    public void changeUserPassword(final @NonNull @PathVariable UUID userId, final @Valid @NonNull @RequestBody ChangePasswordData data) {
        serviceExecutor.executeCommand(UpdateUserPasswordCommand.of(userId, data));
    }

    @PatchMapping("ChangeUserStateCommand")
    public void changeStateOfUser(final @Valid @NonNull @RequestBody ChangeUserStateCommand command) {
        serviceExecutor.executeCommand(command);
    }

    @DeleteMapping("DeleteUserCommand")
    public void deleteUserById(final @Valid @NonNull @RequestBody DeleteUserCommand command) {
        serviceExecutor.executeCommand(command);
    }
}