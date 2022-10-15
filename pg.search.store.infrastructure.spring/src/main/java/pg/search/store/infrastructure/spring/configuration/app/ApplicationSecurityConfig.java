package pg.search.store.infrastructure.spring.configuration.app;

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

import pg.search.store.infrastructure.spring.configuration.auth.FormLoginAuthenticationFilter;
import pg.search.store.infrastructure.spring.configuration.auth.JwtTokenFilter;
import pg.search.store.infrastructure.user.UserService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableScheduling
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final String jwtSecret;

    @Autowired
    public ApplicationSecurityConfig(final UserService userService,
                                     final BCryptPasswordEncoder bCryptPasswordEncoder,
                                     final @Value("${jwt.secret}") String jwtSecret) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtSecret = jwtSecret;
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
                .addFilterAfter(new JwtTokenFilter(jwtSecret), FormLoginAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/index", "/css/*", "/js/*", "/swagger-ui.html").permitAll()

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
