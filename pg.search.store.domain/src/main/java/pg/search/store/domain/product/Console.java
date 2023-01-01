package pg.search.store.domain.product;

import java.util.Arrays;
import java.util.List;

public enum Console {
    XBOX_ONE, XBOX_ONE_X, XBOX_SERIES_S, XBOX_SERIES_X, PS4, PS4_PRO, PS5, STEAM_DECK, NINTENDO_SWITCH;

    public static List<String> toList() {
        return Arrays.stream(Console.values()).map(Console::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}