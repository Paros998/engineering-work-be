package pg.search.store.domain.product;

import java.util.Arrays;
import java.util.List;

public enum RamType {
    DDR2, DDR3, DDR4, DDR5;

    public static List<String> toList() {
        return Arrays.stream(RamType.values()).map(RamType::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}