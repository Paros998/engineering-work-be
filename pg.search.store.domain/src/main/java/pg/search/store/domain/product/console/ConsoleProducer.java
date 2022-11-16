package pg.search.store.domain.product.console;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConsoleProducer {
    MICROSOFT("Microsoft"),
    SONY("Sony"),
    NINTENDO("Nintendo");
    private final String label;
}