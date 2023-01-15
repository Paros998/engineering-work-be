package pg.search.store.spring.configuration.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import pg.search.store.infrastructure.user.UserService;
import pg.search.store.spring.configuration.auth.FormLoginAuthenticationFilter;
import pg.search.store.spring.configuration.auth.JwtTokenFilter;
import pg.search.store.spring.configuration.auth.JwtTokenRefresher;

import static pg.search.store.domain.user.Roles.ADMIN;
import static pg.search.store.domain.user.Roles.CLIENT;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableScheduling
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String jwtSecret;
    private final JwtTokenRefresher tokenRefresher;

    @Autowired
    public ApplicationSecurityConfiguration(final UserService userService,
                                            final BCryptPasswordEncoder bCryptPasswordEncoder,
                                            final @Value("${jwt.secret}") String jwtSecret,
                                            final JwtTokenRefresher tokenRefresher) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtSecret = jwtSecret;
        this.tokenRefresher = tokenRefresher;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new FormLoginAuthenticationFilter(authenticationManager(), userService, jwtSecret))
                .addFilterAfter(new JwtTokenFilter(jwtSecret, tokenRefresher), FormLoginAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/index", "/css/*", "/js/*", "/swagger-ui.html").permitAll()
                .antMatchers("**/login", "**/users/register-client", "**/auth/refresh-access").anonymous()
                .antMatchers("**/notifications/**").hasAnyRole(ADMIN.name(), CLIENT.name())
        ;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}