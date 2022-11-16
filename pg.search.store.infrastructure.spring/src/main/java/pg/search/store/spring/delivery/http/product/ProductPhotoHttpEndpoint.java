package pg.search.store.spring.delivery.http.product;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.application.cqrs.product.command.photo.DeleteProductPhotoCommand;
import pg.search.store.application.cqrs.product.command.photo.UpdateProductPhotoCommand;
import pg.search.store.application.cqrs.product.command.photo.UploadProductPhotoCommand;
import pg.search.store.application.cqrs.product.query.photo.ProductPhotoQuery;
import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

import java.util.UUID;

@RestController
@RequestMapping(HttpCommonHelper.PRODUCT_PHOTO_PATH)
@AllArgsConstructor
@Tag(name = "Product-Photo")
public class ProductPhotoHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

    @GetMapping("{productId}")
    public String getProductPhotoUrl(final @NonNull @PathVariable UUID productId) {
        final var query = ProductPhotoQuery.of(productId);
        return serviceExecutor.executeQuery(query);
    }

    @PostMapping(value = "{productId}", consumes = {"multipart/form-data"})
    public void uploadProductPhoto(final @NonNull @PathVariable UUID productId, final @RequestParam("file") MultipartFile file) {
        final var command = UploadProductPhotoCommand.of(productId, file);
        serviceExecutor.executeCommand(command);
    }

    @PutMapping(value = "{productId}", consumes = {"multipart/form-data"})
    public void updateProductPhoto(final @NonNull @PathVariable UUID productId, final @RequestParam("file") MultipartFile file) {
        final var command = UpdateProductPhotoCommand.of(productId, file);
        serviceExecutor.executeCommand(command);
    }

    @DeleteMapping("{productId}")
    public void deleteProductPhoto(final @NonNull @PathVariable UUID productId) {
        final var command = DeleteProductPhotoCommand.of(productId);
        serviceExecutor.executeCommand(command);
    }
}