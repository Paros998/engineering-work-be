package pg.search.store.application.cqrs.user.command.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.web.multipart.MultipartFile;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UpdateUserAvatarCommand implements Command<Void> {
    private final UUID userId;
    private final MultipartFile file;
}
