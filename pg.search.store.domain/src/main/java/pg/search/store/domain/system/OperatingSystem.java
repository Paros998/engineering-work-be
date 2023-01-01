package pg.search.store.domain.system;

import java.util.Arrays;
import java.util.List;

public enum OperatingSystem {
    WIN_7,
    WIN_8,
    WIN_8_1,
    WIN_10,
    WIN_11,
    MAC_OS;

    public static List<String> toList() {
        return Arrays.stream(OperatingSystem.values()).map(OperatingSystem::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }

}