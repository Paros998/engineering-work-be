package pg.search.store.application.cqrs.product.command.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.web.multipart.MultipartFile;

import pg.lib.cqrs.command.Command;

import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
public class UpdateProductPhotoCommand implements Command<Void> {
    private final UUID productId;
    private final MultipartFile file;
}
