package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.awsfiles.entity.FileEntity;
import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileService fileService;
    private final ProductRepository productRepository;

    public SpringPageResponse<ProductEntity> getProducts(final SpringPageRequest request, final ProductType productType) {
        final PageRequest pageable = request.getRequest(ProductEntity.class);

        if (productType != null)
            return new SpringPageResponse<>(productRepository.getProducts(productType, pageable));

        return new SpringPageResponse<>(productRepository.findAll(pageable));
    }

    public ProductEntity getEntityById(final UUID productId) {
        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isPresent())
            return product.get();
        else throw new EntityNotFoundException(productId, ProductEntity.class);
    }

    public String getProductPhoto(final ProductEntity entity) {
        if (entity.getProductPhoto() == null)
            return fileService.getFileUrl(Files.getDefaultProductPhoto());

        return fileService.getFileUrl(entity.getProductPhoto().getFileId());
    }

    public String getProductPhotoById(final UUID productId) {
        final ProductEntity entity = getEntityById(productId);

        return getProductPhoto(entity);
    }

    public void uploadProductPhoto(final UUID productId, final MultipartFile file) {
        final ProductEntity product = getEntityById(productId);

        final UUID photoId = fileService.uploadFile(file);

        product.setProductPhoto(fileService.getFileById(photoId));

        productRepository.save(product);
    }

    public void updateProductPhoto(final UUID productId, final MultipartFile file) {
        final ProductEntity product = getEntityById(productId);

        final UUID newPhotoId = fileService.uploadFile(file);

        final FileEntity oldPhoto = product.productPhoto;

        if (oldPhoto != null) {
            product.setProductPhoto(null);

            fileService.deleteFile(oldPhoto.getFileId());
        }

        product.setProductPhoto(fileService.getFileById(newPhotoId));

        productRepository.save(product);
    }

    public void deleteProductPhoto(final UUID productId) {
        final ProductEntity product = getEntityById(productId);

        final FileEntity photo = product.productPhoto;

        product.setProductPhoto(null);

        productRepository.save(product);

        fileService.deleteFile(photo.getFileId());
    }
}