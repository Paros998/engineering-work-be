package pg.search.store.infrastructure.spring.configuration.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
 
@Configuration
public class CorsConfiguration {

    private static final String ACAO = "Access-Control-Allow-Origin";

    @Bean
    public CorsFilter corsFilter() {
        org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5555",
                "http://localhost:19006"
        ));

        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", ACAO, "Content-Type", "Accept",
                "Authorization", "Origin , Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers",
                "Authorization-Refresh"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
                "Authorization", ACAO, ACAO, "Access-Control-Allow-Credentials",
                "Authorization-Refresh"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
