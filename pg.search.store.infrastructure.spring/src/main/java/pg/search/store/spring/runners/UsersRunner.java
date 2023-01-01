package pg.search.store.spring.runners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import pg.search.store.domain.user.Roles;
import pg.search.store.domain.user.UserCredentialsData;
import pg.search.store.infrastructure.user.UserService;

@Slf4j
@AllArgsConstructor
@Component
public class UsersRunner implements ApplicationRunner {
    private final UserService userService;

    @Override
    public void run(final ApplicationArguments args) {
        try {
            userService.createUser(UserCredentialsData.builder()
                    .username("Admin123")
                    .password("Admin321123")
                    .role(Roles.ADMIN)
                    .email("patrykg198@gmail.com")
                    .build());
        } catch (ResponseStatusException e) {
            log.info("User Seeded -> exc? :" + e);
        }
        
        try {
            userService.createUser(UserCredentialsData.builder()
                    .username("Client123")
                    .password("Client321123")
                    .role(Roles.CLIENT)
                    .email("part4@op.pl")
                    .build());
        } catch (ResponseStatusException e) {
            log.info("User Seeded -> exc? :" + e);
        }
    }
}