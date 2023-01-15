package pg.search.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import pg.search.store.common.SearchStoreIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SearchStoreIntegrationTest
class ApplicationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void shouldAppStartupCorrectly() {
        assertNotNull(applicationContext);
    }
}