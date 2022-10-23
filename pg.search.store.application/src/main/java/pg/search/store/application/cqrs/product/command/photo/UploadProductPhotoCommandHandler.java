package pg.search.store.application.cqrs.product.command.photo;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.infrastructure.product.ProductService;

@Service
@AllArgsConstructor
public class UploadProductPhotoCommandHandler implements CommandHandler<UploadProductPhotoCommand, Void> {
    private final ProductService productService;

    public Void handle(final UploadProductPhotoCommand command) {
        productService.uploadProductPhoto(command.getProductId(), command.getFile());
        return null;
    }
}
