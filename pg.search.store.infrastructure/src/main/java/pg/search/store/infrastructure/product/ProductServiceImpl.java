package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.awsfiles.entity.FileEntity;
import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.Performance;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.filters.ResolvedFilter;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileService fileService;
    private final ProductRepository productRepository;
    private final CacheManager cacheManager;

    final Cache filterCache = cacheManager.getCache("filters");
    final Cache performanceCache = cacheManager.getCache("performances");

    public SpringPageResponse<ProductEntity> getProducts(final SpringPageRequest request, final ProductType productType) {
        final PageRequest pageable = request.getRequest(ProductEntity.class);

        if (productType != null)
            return new SpringPageResponse<>(productRepository.getProducts(productType, pageable));

        return new SpringPageResponse<>(productRepository.findAll(pageable));
    }

    public List<ProductEntity> getProductsByIds(final List<UUID> products) {
        return productRepository.findAllById(products);
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

    public ResolvedFilter resolveFilter(final GamesFilter filter, final String queryMeta) {
        ResolvedFilter resolvedFilter = new ResolvedFilter();

        if (filterCache != null) {
            filterCache.put(queryMeta, resolvedFilter);
        } else log.warn("filters cache is null");

        return resolvedFilter;
    }

    public Map<Class<? extends ProductEntity>, Performance> resolveTargetGamesPerformance(final GamesFilter filter, final String queryMeta) {
        Map<Class<? extends ProductEntity>, Performance> performanceMap = new HashMap<>();


        if (performanceCache != null) {
            performanceCache.put(queryMeta, performanceMap);
        } else log.warn("performances cache is null");

        return performanceMap;
    }

    public SpringPageResponse<ProductEntity> queryData(final ResolvedFilter filter) {
        return null;
    }
}