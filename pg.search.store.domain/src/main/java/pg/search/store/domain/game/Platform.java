package pg.search.store.domain.game;

import java.util.Arrays;
import java.util.List;

public enum Platform {
    PC, XBOX_ONE, XBOX_ONE_X, XBOX_SERIES_S, XBOX_SERIES_X, PS4, PS4_PRO, PS5, STEAM_DECK, NINTENDO_SWITCH;

    public static List<String> toList() {
        return Arrays.stream(Platform.values()).map(Platform::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}