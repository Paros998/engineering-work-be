package pg.search.store.infrastructure.product.exception;

import lombok.NonNull;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class CardAlreadyExistsException extends NestedRuntimeException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private final String name;

    public CardAlreadyExistsException(final @NonNull String name) {
        super("");
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "%s [%d %s] ; Graphic card with name: %s already exists in database".formatted(this.getClass(), status.value(),
                status.name(), name);
    }
}
