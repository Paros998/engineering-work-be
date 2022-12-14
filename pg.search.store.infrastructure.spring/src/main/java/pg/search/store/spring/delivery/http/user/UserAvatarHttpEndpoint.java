package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.user.command.avatar.DeleteUserAvatarCommand;
import pg.search.store.application.cqrs.user.command.avatar.UpdateUserAvatarCommand;
import pg.search.store.application.cqrs.user.command.avatar.UploadUserAvatarCommand;
import pg.search.store.application.cqrs.user.query.avatar.UserAvatarUrlQuery;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.USER_AVATAR_PATH)
@AllArgsConstructor
@Tag(name = "User-Avatar")
public class UserAvatarHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{userId}")
    public String getUserAvatarUrl(final @PathVariable UUID userId) {
        final UserAvatarUrlQuery avatarUrlQuery = UserAvatarUrlQuery.of(userId);
        return serviceExecutor.executeQuery(avatarUrlQuery);
    }

    @PostMapping(value = "{userId}", consumes = {"multipart/form-data"})
    public void uploadUserAvatar(final @PathVariable UUID userId, final @RequestParam("file") MultipartFile file) {
        final UploadUserAvatarCommand avatarCommand = UploadUserAvatarCommand.of(userId, file);
        serviceExecutor.executeCommand(avatarCommand);
    }

    @PutMapping(value = "{userId}", consumes = {"multipart/form-data"})
    public void updateUserAvatar(final @PathVariable UUID userId, final @RequestParam("file") MultipartFile file) {
        final UpdateUserAvatarCommand avatarCommand = UpdateUserAvatarCommand.of(userId, file);
        serviceExecutor.executeCommand(avatarCommand);
    }

    @DeleteMapping("{userId}")
    public void deleteUserAvatar(final @PathVariable UUID userId) {
        final DeleteUserAvatarCommand deleteAvatarCommand = DeleteUserAvatarCommand.of(userId);
        serviceExecutor.executeCommand(deleteAvatarCommand);
    }
}