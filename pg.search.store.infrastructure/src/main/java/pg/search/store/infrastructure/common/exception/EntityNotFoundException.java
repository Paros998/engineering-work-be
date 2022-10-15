package pg.search.store.infrastructure.common.exception;

import lombok.Getter;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.UUID;

@Getter
@SuppressWarnings("rawtypes")
public class EntityNotFoundException extends NestedRuntimeException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private final Class clazz;
    private UUID id;
    private String reason;

    public EntityNotFoundException(final UUID id, final Class clazz) {
        super("");
        this.id = id;
        this.clazz = clazz;
    }

    public EntityNotFoundException(final String reason, final Class clazz) {
        super("");
        this.reason = reason;
        this.clazz = clazz;
    }

    @Override
    public String getMessage() {

        if (Objects.nonNull(reason))
            return "%s [%d %s] ; Entity of %s class %s".formatted(this.getClass(), status.value(), status.name(), clazz, reason);

        if (Objects.nonNull(id))
            return "%s [%d %s] ; Entity of %s class with identifier: %s - not found".formatted(this.getClass(), status.value(), status.name()
                    , clazz, id);

        return "%s [%d %s]".formatted(this.getClass(), status.value(), status.name());
    }
}
