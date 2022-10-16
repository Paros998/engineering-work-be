package pg.search.store.spring.delivery.http;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.search.store.spring.configuration.auth.JwtTokenRefresher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pg.search.store.spring.delivery.http.common.HttpCommonHelper.AUTH_PATH;

@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
@Tag(name = "Authorization")
public class AuthorizationHttpEndpoint {
    private final JwtTokenRefresher tokenRefresher;

    @PostMapping("refresh-access")
    public void refreshAccessToken(final HttpServletRequest request, final HttpServletResponse response) {
        tokenRefresher.refreshToken(request, response);
    }
}
