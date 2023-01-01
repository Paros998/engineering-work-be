package pg.search.store.infrastructure.product.exception;

import lombok.NonNull;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends NestedRuntimeException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private final String name;

    public ProductAlreadyExistsException(final @NonNull String name) {
        super("");
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "%s [%d %s] ; Product with name: %s already exists in database".formatted(this.getClass(), status.value(), status.name(),
                name);
    }
}
