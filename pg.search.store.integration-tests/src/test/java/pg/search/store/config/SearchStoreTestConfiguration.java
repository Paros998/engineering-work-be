package pg.search.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pg.search.store.spring.SearchStoreModuleConfiguration;

@Configuration
@Import({
        SearchStoreModuleConfiguration.class
})
public class SearchStoreTestConfiguration {
}