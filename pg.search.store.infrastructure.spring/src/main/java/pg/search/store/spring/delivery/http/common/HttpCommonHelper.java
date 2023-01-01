package pg.search.store.spring.delivery.http.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpCommonHelper {
    public static final String BASE_PATH = "api/v1";

    public static final String AUTH_PATH = BASE_PATH + "/auth";

    public static final String COMMON_DATA_PATH = BASE_PATH + "/common-data";

    public static final String USER_PATH = BASE_PATH + "/users";
    public static final String USER_AVATAR_PATH = USER_PATH + "/avatar";
    public static final String NOTIFICATION_PATH = BASE_PATH + "/notifications";
    public static final String CARD_PATH = BASE_PATH + "/cards";
    public static final String CPU_PATH = BASE_PATH + "/processors";

    public static final String PC_PATH = BASE_PATH + "/computers";
    public static final String CONSOLE_PATH = BASE_PATH + "/consoles";

    public static final String LAPTOP_PATH = BASE_PATH + "/laptops";
    public static final String PRODUCT_PATH = BASE_PATH + "/products";
    public static final String PRODUCT_PHOTO_PATH = PRODUCT_PATH + "/photo";
    public static final String PRODUCT_REVIEWS_PATH = PRODUCT_PATH + "/reviews";
    public static final String PRODUCT_SUGGESTED_PATH = PRODUCT_PATH + "/suggested";
    public static final String PRODUCT_OFFERS = PRODUCT_PATH + "/offers";

    public static final String REVIEWS_PATH = BASE_PATH + "/reviews";
    public static final String GAME_PATH = BASE_PATH + "/games";
    public static final String HISTORY_PATH = BASE_PATH + "/history";
    public static final String STORE_PATH = BASE_PATH + "/stores";
}
