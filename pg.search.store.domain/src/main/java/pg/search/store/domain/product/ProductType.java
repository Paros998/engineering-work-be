package pg.search.store.domain.product;

import java.util.Arrays;
import java.util.List;

public enum ProductType {
    PC,
    LAPTOP,
    CONSOLE,
    GPU,
    CPU;

    public static List<String> toList() {
        return Arrays.stream(ProductType.values()).map(ProductType::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}
