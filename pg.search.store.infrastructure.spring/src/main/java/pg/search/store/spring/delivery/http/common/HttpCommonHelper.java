package pg.search.store.spring.delivery.http.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpCommonHelper {
    public static final String BASE_PATH = "api/v1";

    public static final String AUTH_PATH = BASE_PATH + "/auth";

    public static final String USER_PATH = BASE_PATH + "/users";

    public static final String USER_AVATAR_PATH = USER_PATH + "/avatar";

    public static final String CARD_PATH = BASE_PATH + "/cards";

    public static final String PRODUCT_PATH = BASE_PATH + "/products";
    public static final String PRODUCT_PHOTO_PATH = PRODUCT_PATH + "/photo";
}
