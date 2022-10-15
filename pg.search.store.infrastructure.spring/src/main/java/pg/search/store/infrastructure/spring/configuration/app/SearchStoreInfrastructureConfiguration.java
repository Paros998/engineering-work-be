package pg.search.store.infrastructure.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pg.lib.awsfiles.service.FileService;

import pg.search.store.infrastructure.product.suggested.SuggestedProductRepository;
import pg.search.store.infrastructure.product.suggested.SuggestedProductService;
import pg.search.store.infrastructure.product.suggested.SuggestedProductServiceImpl;
import pg.search.store.infrastructure.store.StoreRepository;
import pg.search.store.infrastructure.store.StoreService;
import pg.search.store.infrastructure.store.StoreServiceImpl;
import pg.search.store.infrastructure.user.UserRepository;
import pg.search.store.infrastructure.user.UserService;
import pg.search.store.infrastructure.user.UserServiceImpl;

@Configuration
public class SearchStoreInfrastructureConfiguration {
    @Bean
    UserService userService(final BCryptPasswordEncoder passwordEncoder, final UserRepository repository, final FileService fileService) {
        return new UserServiceImpl(passwordEncoder, repository, fileService);
    }

    @Bean
    SuggestedProductService suggestedProductService(final SuggestedProductRepository repository) {
        return new SuggestedProductServiceImpl(repository);
    }

    @Bean
    StoreService storeService(final StoreRepository repository) {
        return new StoreServiceImpl(repository);
    }
}
