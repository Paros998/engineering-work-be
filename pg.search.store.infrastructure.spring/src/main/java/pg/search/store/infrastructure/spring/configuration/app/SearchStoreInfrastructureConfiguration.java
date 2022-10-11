package pg.search.store.infrastructure.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pg.lib.awsfiles.service.IFileService;
import pg.search.store.application.cqrs.TestCommandHandler;
import pg.search.store.infrastructure.repository.UserRepository;
import pg.search.store.infrastructure.service.IUserService;
import pg.search.store.infrastructure.service.UserService;

@Configuration
public class SearchStoreInfrastructureConfiguration {

    @Bean
    TestCommandHandler testCommandHandler() {
        return new TestCommandHandler();
    }

    @Bean
    IUserService userService(final BCryptPasswordEncoder passwordEncoder, final UserRepository repository, final IFileService fileService) {
        return new UserService(passwordEncoder, repository, fileService);
    }
}
