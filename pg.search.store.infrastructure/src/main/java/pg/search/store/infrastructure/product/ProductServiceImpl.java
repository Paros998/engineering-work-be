package pg.search.store.infrastructure.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pg.lib.awsfiles.entity.FileEntity;
import pg.lib.awsfiles.service.FileService;
import pg.lib.filters.common.Criteria;
import pg.lib.filters.common.JunctionType;
import pg.lib.filters.common.Operation;
import pg.lib.filters.exception.EmptySpecificationException;
import pg.lib.filters.specification.Combiner;
import pg.lib.filters.specification.SpecificationBuilder;
import pg.lib.filters.specification.SpecificationCollector;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.Performance;
import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardRepository;
import pg.search.store.infrastructure.product.console.ConsoleEntity;
import pg.search.store.infrastructure.product.console.ConsoleRepository;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.cpu.CpuRepository;
import pg.search.store.infrastructure.product.filters.ResolvedFilter;
import pg.search.store.infrastructure.product.laptop.LaptopEntity;
import pg.search.store.infrastructure.product.laptop.LaptopRepository;
import pg.search.store.infrastructure.product.pc.PcEntity;
import pg.search.store.infrastructure.product.pc.PcRepository;
import pg.search.store.infrastructure.resolvers.FilterResolver;
import pg.search.store.infrastructure.resolvers.PerformanceResolver;

import javax.annotation.PostConstruct;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileService fileService;

    private final ProductRepository productRepository;
    private final CardRepository cardRepository;
    private final ConsoleRepository consoleRepository;
    private final CpuRepository cpuRepository;
    private final PcRepository pcRepository;
    private final LaptopRepository laptopRepository;

    private final CacheManager cacheManager;

    private final FilterResolver filterResolver;
    private final PerformanceResolver performanceResolver;

    private Cache filterCache;
    private Cache performanceCache;

    @PostConstruct
    void init() {
        filterCache = cacheManager.getCache("filters");
        performanceCache = cacheManager.getCache("performances");
    }

    public SpringPageResponse<ProductEntity> getProducts(final SpringPageRequest request, final ProductType productType,
                                                         final String query) {
        final PageRequest pageable = request.getRequest(ProductEntity.class);

        if (productType != null || query != null) {
            List<SpecificationBuilder> specificationBuilders = new ArrayList<>();
            Map<JunctionType, List<Criteria>> criteria;

            if (query != null) {
                criteria = new HashMap<>();
                criteria.put(JunctionType.OR, List.of(
                        Criteria.builder()
                                .key("title").value(query).operation(Operation.MATCH)
                                .build(),
                        Criteria.builder()
                                .key("producentCode").value(query).operation(Operation.MATCH)
                                .build()
                ));
                specificationBuilders.add(SpecificationBuilder.of(Combiner.AND, criteria));

            }

            // Use JT.OR because JT.AND alone doesn't work
            if (productType != null) {
                criteria = new HashMap<>();
                criteria.put(JunctionType.OR, List.of(
                        Criteria.builder()
                                .key("productType").value(productType).operation(Operation.EQUAL)
                                .build())
                );
                specificationBuilders.add(SpecificationBuilder.of(Combiner.AND, criteria));

            }

            final Specification<ProductEntity> spec = SpecificationCollector.createSpecification(specificationBuilders);

            return new SpringPageResponse<>(productRepository.findAll(spec, pageable));
        }

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
        ResolvedFilter resolvedFilter = getFilter(filter);

        if (filterCache != null) {
            filterCache.put(queryMeta, resolvedFilter);
        } else log.warn("filters cache is null");

        return resolvedFilter;
    }

    private ResolvedFilter getFilter(final GamesFilter filter) {
        ResolvedFilter resolvedFilter;

        switch (filter.getProductType()) {
            case GPU -> resolvedFilter = ResolvedFilter.of(CardEntity.class, filterResolver.resolveForCards(filter));
            case CONSOLE -> resolvedFilter = ResolvedFilter.of(ConsoleEntity.class, filterResolver.resolveForConsoles(filter));
            case CPU -> resolvedFilter = ResolvedFilter.of(CpuEntity.class, filterResolver.resolveForCpus(filter));
            case LAPTOP -> resolvedFilter = ResolvedFilter.of(LaptopEntity.class, filterResolver.resolveForLaptops(filter));
            case PC -> resolvedFilter = ResolvedFilter.of(PcEntity.class, filterResolver.resolveForPcs(filter));
            default -> resolvedFilter = ResolvedFilter.of(ProductEntity.class, List.of());
        }

        return resolvedFilter;
    }

    public Performance resolveTargetGamesPerformance(final GamesFilter filter, final String queryMeta) {
        Performance performance = getPerformance(filter);

        if (performanceCache != null) {
            performanceCache.put(queryMeta, performance);
        } else log.warn("performances cache is null");

        return performance;
    }

    private Performance getPerformance(final GamesFilter filter) {
        Performance performance;

        switch (filter.getProductType()) {
            case GPU -> performance = performanceResolver.resolveForCards(filter);
            case CPU -> performance = performanceResolver.resolveForCpus(filter);
            case PC -> performance = performanceResolver.resolveForPCs(filter);
            case LAPTOP -> performance = performanceResolver.resolveForLaptops(filter);
            default -> performance = Performance.builder().averagePerformance(100.).peakPerformance(100.).build();
        }

        return performance;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SpringPageResponse<ProductEntity> queryData(final ResolvedFilter filter, final SpringPageRequest request) {
        Page pageResponse;
        final Specification specification = SpecificationCollector.createSpecification(filter.getSpecificationBuilders());

        switch (filter.getClazz().getSimpleName()) {
            case "CardEntity" -> pageResponse = querySpecificData(cardRepository, cardRepository, specification, request, CardEntity.class);
            case "ConsoleEntity" -> pageResponse = querySpecificData(consoleRepository, consoleRepository, specification, request,
                    ConsoleEntity.class);
            case "CpuEntity" -> pageResponse = querySpecificData(cpuRepository, cpuRepository, specification, request, CpuEntity.class);
            case "PcEntity" -> pageResponse = querySpecificData(pcRepository, pcRepository, specification, request, PcEntity.class);
            case "LaptopEntity" ->
                    pageResponse = querySpecificData(laptopRepository, laptopRepository, specification, request, LaptopEntity.class);
            default -> pageResponse = querySpecificData(productRepository, productRepository, specification, request, ProductEntity.class);
        }

        return new SpringPageResponse<>(pageResponse);
    }

    public boolean existsById(final UUID productId) {
        return productRepository.existsById(productId);
    }

    public <T> Page<T> querySpecificData(final JpaSpecificationExecutor<T> executor, final JpaRepository<T, ?> repository,
                                         final Specification<T> specification, final SpringPageRequest request,
                                         final Class<? extends ProductEntity> clazz) {
        try {
            return executor.findAll(specification, request.getRequest(clazz));
        } catch (EmptySpecificationException e) {
            return repository.findAll(request.getRequest(clazz));
        }
    }
}