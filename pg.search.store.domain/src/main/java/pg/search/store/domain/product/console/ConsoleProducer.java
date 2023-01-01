package pg.search.store.domain.product.console;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum ConsoleProducer {
    MICROSOFT("Microsoft"),
    SONY("Sony"),
    NINTENDO("Nintendo");
    private final String label;

    public static List<String> toList() {
        return Arrays.stream(ConsoleProducer.values()).map(ConsoleProducer::name).toList();
    }

    public static boolean isValidType(final String data) {
        return toList().contains(data);
    }
}