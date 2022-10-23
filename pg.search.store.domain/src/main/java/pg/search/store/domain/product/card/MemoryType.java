package pg.search.store.domain.product.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum MemoryType {
    GDDR3(2),
    GDDR4(2),
    GDDR5(4),
    GDDR5X(8),
    GDDR6(8),
    GDDR6X(16),
    HBM(2),
    HBM2(2),
    HBM2E(2);
    private final Integer scale;

    public static List<String> toList() {
        return Arrays.stream(MemoryType.values()).map(MemoryType::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}
