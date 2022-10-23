package pg.search.store.domain.product.card;

import java.util.Arrays;
import java.util.List;

public enum Technology {
    NVIDIA,
    AMD,
    INTEL;

    public static List<String> toList() {
        return Arrays.stream(Technology.values()).map(Technology::name).toList();
    }

    public static boolean isValidTechnology(final String data) {
        return toList().contains(data);
    }
}
