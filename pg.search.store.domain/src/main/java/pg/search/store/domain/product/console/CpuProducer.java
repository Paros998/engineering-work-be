package pg.search.store.domain.product.console;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum CpuProducer {
    INTEL("Intel"),
    AMD("Amd");
    private final String label;

    public static List<String> toList() {
        return Arrays.stream(CpuProducer.values()).map(CpuProducer::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}