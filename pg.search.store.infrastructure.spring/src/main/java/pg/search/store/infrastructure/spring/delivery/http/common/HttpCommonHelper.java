package pg.search.store.infrastructure.spring.delivery.http.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpCommonHelper {
    public static final String BASE_PATH = "api/v1";

    public static final String AUTH_PATH = BASE_PATH + "/auth";
}
