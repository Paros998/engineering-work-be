package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.common.Files;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileService fileService;

    public String getProductPhoto(final ProductEntity entity) {
        if (entity.getProductId() == null)
            return fileService.getFileUrl(Files.getDefaultProductPhoto());

        return fileService.getFileUrl(entity.getProductId());
    }
}
